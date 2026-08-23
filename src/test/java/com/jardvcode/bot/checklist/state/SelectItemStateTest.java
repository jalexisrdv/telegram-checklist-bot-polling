package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.dto.*;
import com.jardvcode.bot.checklist.entity.ResponseEntity;
import com.jardvcode.bot.checklist.entity.ResponseEntityMother;
import com.jardvcode.bot.checklist.service.SectionService;
import com.jardvcode.bot.checklist.service.ResponseService;
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
class SelectItemStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private BotSessionDataService sessionDataService;

    @Mock
    private ResponseService responseService;

    @Mock
    private SectionService groupService;

    @InjectMocks
    private SelectItemState state;

    @Test
    void shouldSendItemsMessageWhenGroupSelected() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long mechanicUserId = 1L;
        SectionDTO dto = SectionDTOMother.create();
        ArrayList<ResponseEntity> responses = ResponseEntityMother.withSomeResponses();

        when(botContext.getSystemUserId()).thenReturn(mechanicUserId);
        when(sessionDataService.findByBotUserId(mechanicUserId, SectionDTO.class)).thenReturn(dto);
        when(responseService.findByAssignmentIdAndSectionId(dto.assignmentDTO().assignmentId(), dto.id())).thenReturn(responses);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext, times(2)).sendText(captor.capture());
        List<String> values = captor.getAllValues();
        assertEquals(expectedHeaderMessage(), values.get(0));
        assertEquals(expectedBodyMessageWithSomeResponses(), values.get(1));
        assertNull(decision.nextState());
    }

    @Test
    void shouldSendInvalidOptionMessageWhenOptionDoesNotExist() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        SectionDTO dto = SectionDTOMother.create();
        Long optionNumber = 1L;

        when(responseService.findByAssignmentIdAndSectionIdAndOptionNumber(dto.assignmentDTO().assignmentId(), dto.id(), optionNumber)).thenThrow();
        Decision decision = state.onUserInput(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Opción no valida", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldPersistItemAndMoveToNextStateWhenOptionIsValid() throws Exception {
        String message = "1";
        Long mechanicUserId = 1L;
        Long optionNumber = 1L;
        SectionDTO sectionDTO = SectionDTOMother.create();
        ResponseEntity response = ResponseEntityMother.withPendingItem();
        ItemDTO itemDTO = ItemDTOMother.create();

        when(botContext.getMessage()).thenReturn(message);
        when(botContext.getSystemUserId()).thenReturn(mechanicUserId);
        when(sessionDataService.findByBotUserId(mechanicUserId, SectionDTO.class)).thenReturn(sectionDTO);
        when(responseService.findByAssignmentIdAndSectionIdAndOptionNumber(sectionDTO.assignmentDTO().assignmentId(), sectionDTO.id(), optionNumber)).thenReturn(response);
        Decision decision = state.onUserInput(botContext);

        verify(sessionDataService, times(1)).save(mechanicUserId, itemDTO, SelectItemState.class);
        assertEquals(AnswerItemState.class, decision.nextState());
    }

    private String expectedHeaderMessage() {
        return String.format("""
                📋 Formato para servicios A y C (BASICO)
                👤 Operador: PEDRO OCELOT
                📅 Fecha: %s
                📂 Grupo: SISTEMA DE DIRECCION
                🔍 Envía el número del punto de inspección que deseas responder:
                
                """, LocalDate.now().toString());
    }

    private String expectedBodyMessageWithSomeResponses() {
        return """
                ✅ 1. REVISION DE FUGAS (ACEITE, AGUA, DIESEL)
                   OK OBSERVATION
                
                ⏳ 2. RESET INSITE
                \s\s\s
                
                ✅ 3. CAMBIO DE FILTROS (DIESEL)
                   OK OBSERVATION
                
                ⏳ 4. REVISAR TENSION DE BANDAS
                \s\s\s
                
                ✅ 5. NIVEL DE REFRIGERANTE
                   OK OBSERVATION
                
                ⏳ 6. CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS
                \s\s\s
                
                """;
    }

}