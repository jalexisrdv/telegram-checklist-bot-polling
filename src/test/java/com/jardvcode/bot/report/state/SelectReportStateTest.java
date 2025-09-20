package com.jardvcode.bot.report.state;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntityMother;
import com.jardvcode.bot.checklist.service.InstanceService;
import com.jardvcode.bot.report.dto.ChecklistDTO;
import com.jardvcode.bot.report.dto.ChecklistDTOMother;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectReportStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private BotSessionDataService sessionDataService;

    @Mock
    private InstanceService instanceService;

    @InjectMocks
    private SelectReportState state;

    @Test
    void shouldNotSendReportMessageWhenNoChecklistAssigned() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(instanceService.findUnconfirmedByUserId(userId)).thenReturn(List.of());
        Decision decision = state.onBotMessage(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Aún no tienes listas de inspección asignadas, por lo que no es posible generar un reporte.", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldSendReportMessageWhenChecklistAssigned() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;
        ArrayList<InstanceEntity> instances = InstanceEntityMother.values();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(instanceService.findUnconfirmedByUserId(userId)).thenReturn(instances);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals(getExpectedMessage(), captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldSendInvalidOptionMessageWhenOptionDoesNotExist() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;
        Long optionNumber = 1L;

        when(instanceService.findByUserIdAndOptionNumber(userId, optionNumber)).thenThrow();
        Decision decision = state.onUserInput(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Opción no valida", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldPersistChecklistAndMoveToNextStateWhenOptionIsValid() throws Exception {
        String message = "1";
        Long userId = 1L;
        Long optionNumber = 1L;
        InstanceEntity instance = InstanceEntityMother.withPendingStatus();
        ChecklistDTO dto = ChecklistDTOMother.create();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(botContext.getMessage()).thenReturn(message);
        when(instanceService.findByUserIdAndOptionNumber(userId, optionNumber)).thenReturn(instance);
        Decision decision = state.onUserInput(botContext);

        verify(sessionDataService, times(1)).save(userId, dto, SelectReportState.class);
        assertEquals(Decision.state(GenerateReportState.class), decision.nextState());
    }

    private String getExpectedMessage() {
        return String.format("""
                Estas son tus listas de inspección asignadas. Envía el número de la lista que prefieras y generaré un reporte con tus respuestas actuales.
                
                ⏳ 1. Formato para servicios A y C (BASICO)
                   - Unidad: 243
                   - Operador: PEDRO OCELOT
                   - Fecha: %s
                
                ✅ 1. Formato para servicios A y C (BASICO)
                   - Unidad: 243
                   - Operador: PEDRO OCELOT
                   - Fecha: %s
                   
                """, LocalDate.now().toString(), LocalDate.now().toString());
    }

}