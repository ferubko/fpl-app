package svf.fpl.edu.bot.dto;

import com.svf.fpl.edu.dto.report.TopFootballerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BestFootballersDto {
    private List<TopFootballerDto> goalkeepers;
    private List<TopFootballerDto> defenders;
    private List<TopFootballerDto> middleFields;
    private List<TopFootballerDto> forwards;
}
