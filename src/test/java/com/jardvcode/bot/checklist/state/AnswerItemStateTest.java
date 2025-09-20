package com.jardvcode.bot.checklist.state;

import com.jardvcode.bot.checklist.dto.ItemDTO;
import com.jardvcode.bot.checklist.dto.ItemDTOMother;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerItemStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private BotSessionDataService sessionDataService;

    @Mock
    private ItemResponseService responseService;

    @InjectMocks
    private AnswerItemState state;

    @Test
    void shouldSendStatusMessageWhenItemSelected() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Long userId = 1L;
        ItemDTO dto = ItemDTOMother.create();

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ItemDTO.class)).thenReturn(dto);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext, times(1)).sendText(captor.capture());
        assertEquals("Envía el estatus de " + dto.description() + ": ", captor.getValue());
        assertNull(decision.nextState());
    }

    @Test
    void shouldSendInvalidFormatMessageWhenInputIsIncorrect() throws Exception {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        String message = "asdad";

        when(botContext.getMessage()).thenReturn(message);
        Decision decision = state.onUserInput(botContext);

        verify(botContext, times(1)).sendText(captor.capture());
        assertEquals(expectedInvalidFormatMessage(), captor.getValue());
        assertEquals(Decision.state(AnswerItemState.class), decision.nextState());
    }

    @Test
    void shouldPersistStatusWhenInputIsCorrect() throws Exception {
        String message = "ok en condiciones";
        Long userId = 1L;
        ItemDTO dto = ItemDTOMother.create();
        String status = "ok";
        String observations = "en condiciones";

        when(botContext.getMessage()).thenReturn(message);
        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ItemDTO.class)).thenReturn(dto);
        Decision decision = state.onUserInput(botContext);

        verify(responseService, times(1)).save(dto.id(), status, observations);
        assertEquals(Decision.state(SelectItemState.class), decision.nextState());
    }

    private String expectedInvalidFormatMessage() {
        return """
                Formato no válido. Responda únicamente con \"F\" (fallas), \"OK\" (en condiciones) o \"R\" (se reparó). Opcionalmente, agregue un espacio seguido de sus observaciones. Ejemplo: F MIS OBSERVACIONES
                """;
    }

}