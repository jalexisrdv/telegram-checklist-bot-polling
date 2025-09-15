package com.jardvcode.bot.checklist.state.checklist;

import com.jardvcode.bot.checklist.dto.*;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntityMother;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntityMother;
import com.jardvcode.bot.checklist.service.InstanceGroupService;
import com.jardvcode.bot.checklist.service.ItemResponseService;
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
    private ItemResponseService itemService;

    @Mock
    private InstanceGroupService groupService;

    @InjectMocks
    private SelectItemState state;

    @Test
    void shouldSendItemsMessageWhenGroupSelected() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;
        GroupDTO dto = GroupDTOMother.create();
        ArrayList<ResponseEntity> items = ResponseEntityMother.withRandomStatus();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, GroupDTO.class)).thenReturn(dto);
        when(itemService.findByInstanceIdAndGroupId(dto.checklistDTO().instanceId(), dto.id())).thenReturn(items);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext, times(2)).sendText(captor.capture());
        List<String> values = captor.getAllValues();
        assertEquals(expectedHeaderMessage(), values.get(0));
        assertEquals(expectedBodyMessageWithRandomStatus(), values.get(1));
        assertNull(decision.nextState());
    }

    @Test
    void shouldCompleteGroupWhenAllResponsesReceived() throws Exception {
        Long userId = 1L;
        GroupDTO dto = GroupDTOMother.create();
        ArrayList<ResponseEntity> items = ResponseEntityMother.withCompletedStatus();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, GroupDTO.class)).thenReturn(dto);
        when(itemService.findByInstanceIdAndGroupId(dto.checklistDTO().instanceId(), dto.id())).thenReturn(items);
        state.onBotMessage(botContext);

        verify(groupService, times(1)).markAsCompleted(dto.checklistDTO().instanceId(), dto.id());
    }

    @Test
    void shouldSendInvalidOptionMessageWhenOptionDoesNotExist() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        GroupDTO dto = GroupDTOMother.create();
        Long optionNumber = 1L;

        when(itemService.findByInstanceIdAndGroupIdAndOptionNumber(dto.checklistDTO().instanceId(), dto.id(), optionNumber)).thenThrow();
        Decision decision = state.onUserInput(botContext);

        verify(botContext).sendText(captor.capture());
        assertEquals("Opción no valida", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldPersistItemAndMoveToNextStateWhenOptionIsValid() throws Exception {
        String message = "1";
        Long userId = 1L;
        Long optionNumber = 1L;
        GroupDTO groupDTO = GroupDTOMother.create();
        ResponseEntity item = ResponseEntityMother.withPendingItem();
        ItemDTO itemDTO = ItemDTOMother.create();

        when(botContext.getMessage()).thenReturn(message);
        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, GroupDTO.class)).thenReturn(groupDTO);
        when(itemService.findByInstanceIdAndGroupIdAndOptionNumber(groupDTO.checklistDTO().instanceId(), groupDTO.id(), optionNumber)).thenReturn(item);
        Decision decision = state.onUserInput(botContext);

        verify(sessionDataService, times(1)).save(userId, itemDTO, SelectItemState.class);
        assertEquals(Decision.state(AnswerItemState.class), decision.nextState());
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

    private String expectedBodyMessageWithRandomStatus() {
        return """
                ⏳ 1. REVISION DE FUGAS (ACEITE, AGUA, DIESEL)
                \s\s\s
                
                ✅ 2. RESET INSITE
                   OK\s
                
                ⏳ 3. CAMBIO DE FILTROS (DIESEL)
                \s\s\s
                
                ✅ 4. REVISAR TENSION DE BANDAS
                   OK\s
                
                ⏳ 5. NIVEL DE REFRIGERANTE
                \s\s\s
                
                ✅ 6. CAMBIAR FILTRO DE AIRE SEGUN INDICADOR DE PARTICULAS
                   OK\s
                
                """;
    }

}