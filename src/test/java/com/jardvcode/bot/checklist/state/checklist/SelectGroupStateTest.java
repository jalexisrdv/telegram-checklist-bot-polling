package com.jardvcode.bot.checklist.state.checklist;

import com.jardvcode.bot.checklist.domain.BotCommand;
import com.jardvcode.bot.checklist.dto.ChecklistDTO;
import com.jardvcode.bot.checklist.dto.ChecklistDTOMother;
import com.jardvcode.bot.checklist.dto.GroupDTO;
import com.jardvcode.bot.checklist.dto.GroupDTOMother;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntityMother;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.service.InstanceGroupService;
import com.jardvcode.bot.checklist.service.InstanceService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectGroupStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private BotSessionDataService sessionDataService;

    @Mock
    private InstanceGroupService groupService;

    @Mock
    private InstanceService instanceService;

    @InjectMocks
    private SelectGroupState state;

    @Test
    void shouldNotSendGroupsMessageWhenNoChecklistSelected() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ChecklistDTO.class)).thenThrow(DataNotFoundException.class);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Aún no has seleccionado una lista de inspección. Envía o pulsa " + BotCommand.CHECKLISTS.value() + " para ver las listas disponibles.", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldSendGroupsMessageWhenChecklistSelected() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;
        ChecklistDTO dto = ChecklistDTOMother.create();
        ArrayList<InstanceGroupEntity> groups = InstanceGroupEntityMother.withRandomStatus();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ChecklistDTO.class)).thenReturn(dto);
        when(groupService.findByInstanceId(dto.instanceId())).thenReturn(groups);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals(expectedMessageWithRandomStatus(), captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldCompleteChecklistWhenAllResponsesReceived() throws Exception {
        Long userId = 1L;
        ChecklistDTO dto = ChecklistDTOMother.create();
        ArrayList<InstanceGroupEntity> groups = InstanceGroupEntityMother.withCompletedStatus();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ChecklistDTO.class)).thenReturn(dto);
        when(groupService.findByInstanceId(dto.instanceId())).thenReturn(groups);
        state.onBotMessage(botContext);

        verify(instanceService, times(1)).markAsCompleted(dto.instanceId());
    }

    @Test
    void shouldSendInvalidOptionMessageWhenOptionDoesNotExist() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ChecklistDTO dto = ChecklistDTOMother.create();
        Long optionNumber = 1L;

        when(groupService.findByInstanceIdAndOptionNumber(dto.instanceId(), optionNumber)).thenThrow();
        Decision decision = state.onUserInput(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Opción no valida", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldPersistGroupAndMoveToNextStateWhenOptionIsValid() throws Exception {
        String message = "1";
        Long userId = 1L;
        Long optionNumber = 1L;
        ChecklistDTO checklistDTO = ChecklistDTOMother.create();
        InstanceGroupEntity group = InstanceGroupEntityMother.withPendingGroup();
        GroupDTO groupDTO = GroupDTOMother.create();

        when(botContext.getMessage()).thenReturn(message);
        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ChecklistDTO.class)).thenReturn(checklistDTO);
        when(groupService.findByInstanceIdAndOptionNumber(checklistDTO.instanceId(), optionNumber)).thenReturn(group);
        Decision decision = state.onUserInput(botContext);

        verify(sessionDataService, times(1)).save(userId, groupDTO, SelectGroupState.class);
        assertEquals(Decision.state(SelectItemState.class), decision.nextState());
    }

    private String expectedMessageWithRandomStatus() {
        return String.format("""
                📋 Formato para servicios A y C (BASICO)
                   - Operador: PEDRO OCELOT
                   - Fecha: %s
                
                📂 Envía el número del grupo para mostrar los puntos de inspección:
                
                ✅ 1. SISTEMA DE DIRECCION
                ⏳ 2. SUSPENCION DELANTERA
                ✅ 3. MOTOR
                ⏳ 4. EMBRAGUE
                ✅ 5. TRANSMISION
                ⏳ 6. DIFERENCIALES
                ✅ 7. QUINTARUEDA
                ⏳ 8. SISTEMA ELECTRICO/ELECTRONICO
                ✅ 9. SISTEMA DE RODAMIENTO
                ⏳ 10. SISTEMA DE FRENOS
                ✅ 11. EQUIPO DE SEGURIDAD
                ⏳ 12. LIMPIEZA Y LUBRICACION
                """, LocalDate.now().toString());
    }

}