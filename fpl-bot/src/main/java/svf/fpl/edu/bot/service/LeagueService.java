package svf.fpl.edu.bot.service;

import com.svf.fpl.edu.dto.report.ElementType;
import com.svf.fpl.edu.dto.report.TopFootballerDto;
import com.svf.fpl.edu.service.FootballerStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import svf.fpl.edu.bot.dto.BestFootballersDto;

import java.util.List;
import java.util.Map;

@Service
public class LeagueService {
    @Autowired
    private FootballerStatisticService footballerStatisticService;

    public BestFootballersDto generateTopPlayers(int limit) {
        Map<ElementType, List<TopFootballerDto>> bestFootballers = footballerStatisticService.getBestFootballers(limit);
        return BestFootballersDto.builder()
                .goalkeepers(bestFootballers.get(ElementType.GKP))
                .defenders(bestFootballers.get(ElementType.DEF))
                .middleFields(bestFootballers.get(ElementType.MID))
                .forwards(bestFootballers.get(ElementType.FWD))
                .build();
    }
}
