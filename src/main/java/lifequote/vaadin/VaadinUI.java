//package lifequote.vaadin;
//import lifequote.domain.QuoteUIModel;
//import lifequote.domain.QuoteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//@SpringUI
//@Theme("valo")
//public class VaadinUI extends UI {
//
//    private final QuoteRepository repo;
//
//    private final QuoteEditor editor;
//
//    final Grid grid;
//
//    final TextField filter;
//
//    private final Button addNewBtn;
//
//    @Autowired
//    public VaadinUI(QuoteRepository repo, QuoteEditor editor) {
//        this.repo = repo;
//        this.editor = editor;
//        this.grid = new Grid();
//        this.filter = new TextField();
//        this.addNewBtn = new Button("New quote", FontAwesome.PLUS);
//    }
//
//    @Override
//    protected void init(VaadinRequest request) {
//        // build layout
//        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
//        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
//        setContent(mainLayout);
//
//        // Configure layouts and components
//        actions.setSpacing(true);
//        mainLayout.setMargin(true);
//        mainLayout.setSpacing(true);
//
//        grid.setHeight(300, Unit.PIXELS);
//        grid.setColumns("id", "name", "description","image");
//
//        filter.setInputPrompt("Filter by  name");
//
//        // Hook logic to components
//
//        // Replace listing with filtered content when user changes filter
//        filter.addTextChangeListener(e -> listCustomers(e.getText()));
//
//        // Connect selected Customer to editor or hide if none is selected
//        grid.addSelectionListener(e -> {
//            if (e.getSelected().isEmpty()) {
//                editor.setVisible(false);
//            }
//            else {
//                editor.editCustomer((QuoteUIModel) grid.getSelectedRow());
//            }
//        });
//
//        // Instantiate and edit new Customer the new button is clicked
//        addNewBtn.addClickListener(e -> editor.editCustomer(new QuoteUIModel()));
//
//        // Listen changes made by the editor, refresh data from backend
//        editor.setChangeHandler(() -> {
//            editor.setVisible(false);
//            listCustomers(filter.getValue());
//        });
//
//        // Initialize listing
//        listCustomers(null);
//    }
//
//    // tag::listCustomers[]
//    void listCustomers(String text) {
//        if (StringUtils.isEmpty(text)) {
//            grid.setContainerDataSource(
//                    new BeanItemContainer(QuoteUIModel.class, repo.findAll()));
//        }
//        else {
//            grid.setContainerDataSource(new BeanItemContainer(QuoteUIModel.class,
//                    repo.findByName(text)));
//        }
//    }
//    // end::listCustomers[]
//
//}