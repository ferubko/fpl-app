package svf.fpl.edu.bot.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BotUserDto {
    private Long id;
    private Long chatId;
    private String userName;
    private boolean editAllowed;
}
