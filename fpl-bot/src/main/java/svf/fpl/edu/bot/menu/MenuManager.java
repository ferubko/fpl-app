package svf.fpl.edu.bot.menu;

import org.springframework.stereotype.Component;
import svf.fpl.edu.bot.dto.MenuItem;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuManager {

//    public static final String PREV_ACTION = "page-prev";
//    public static final String NEXT_ACTION = "page-next";
//    public static final String CANCEL_ACTION = "cancel";

    private int buttonsPerPage = 6;

    public void setButtonsPerPage(int buttonsPerPage) {
        this.buttonsPerPage = buttonsPerPage;
    }

    private int total;
    private int lastPage;

//    private MenuItem btnPrev = new MenuItem("<<", PREV_ACTION);
//    private MenuItem btnNext = new MenuItem(">>", NEXT_ACTION);
//    private MenuItem btnCancel = new MenuItem("Відмінити", CANCEL_ACTION);

    private List<MenuItem> menu = new ArrayList<>();

    private int columnsCount;

    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public void init() {
        this.total = menu.size();
        this.lastPage = (int) Math.ceil((double) total / buttonsPerPage) - 1;
    }

//    public void addMenuItem(String name, String action) {
//        this.menu.add(new MenuItem(name, action));
//    }
//
//    private List<MenuItem> getPage(int page) {
//        List<MenuItem> pageMenu = new ArrayList<>();
//        if (page > lastPage) {
//            return pageMenu;
//        }
//        int start = page * buttonsPerPage;
//        int end = (page + 1) * buttonsPerPage - 1;
//        if (start < 0) start = 0;
//        if (end >= total) end = total - 1;
//        for (int i = start; i <= end; i++) {
//            pageMenu.add(menu.get(i));
//        }
//        return pageMenu;
//    }

//    private List<MenuItem> getControlButtonsForPage(int page, boolean hasCancel) {
//        List<MenuItem> buttons = new ArrayList<>();
//        if (page > 0) {
//            buttons.add(btnPrev);
//        }
//        if (hasCancel) {
//            buttons.add(btnCancel);
//        }
//        if (page < lastPage) {
//            buttons.add(btnNext);
//        }
//        return buttons;
//    }
//
//    public InlineKeyboardBuilder createMenuForPage(int page, boolean hasCancel) {
//        List<MenuItem> pageButtons = getPage(page);
//        List<MenuItem> controlButtons = getControlButtonsForPage(page, hasCancel);
//        InlineKeyboardBuilder builder = InlineKeyboardBuilder.create();
//        final int[] col = {0};
//        final int[] num = {0};
//        builder.row();
//        pageButtons.forEach(button -> {
//            builder.button(button.getName(), button.getAction());
//            if (++col[0] >= columnsCount) {
//                col[0] = 0;
//                builder.endRow();
//                if (num[0]++ <= pageButtons.size()) {
//                    builder.row();
//                }
//            }
//        });
//        builder.endRow();
//        builder.row();
//        controlButtons.forEach(button -> {
//            switch (button.getAction()) {
//                case PREV_ACTION:
//                    builder.button(button.getName(), button.getAction() + ":" + (page - 1));
//                    break;
//                case NEXT_ACTION:
//                    builder.button(button.getName(), button.getAction() + ":" + (page + 1));
//                    break;
//                default:
//                    builder.button(button.getName(), button.getAction());
//                    break;
//            }
//        });
//        builder.endRow();
//        return builder;
//    }
}
