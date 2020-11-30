package svf.fpl.edu.bot.menu;

import org.springframework.stereotype.Component;
import svf.fpl.edu.bot.dto.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuManager {
    private int buttonsPerPage = 6;

    public void setButtonsPerPage(int buttonsPerPage) {
        this.buttonsPerPage = buttonsPerPage;
    }

    private int total;
    private int lastPage;

    private List<MenuItem> menu = new ArrayList<>();

    private int columnsCount;

    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public void init() {
        this.total = menu.size();
        this.lastPage = (int) Math.ceil((double) total / buttonsPerPage) - 1;
    }
}
