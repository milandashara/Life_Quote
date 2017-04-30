//package lifequote.vaadin;
//
///**
// * Created by milanashara on 12/24/16.
// */
//import com.vaadin.server.Page;
//import com.vaadin.ui.*;
//import lifequote.domain.QuoteUIModel;
//import lifequote.domain.QuoteRepository;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.vaadin.data.fieldgroup.BeanFieldGroup;
//import com.vaadin.event.ShortcutAction;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.themes.ValoTheme;
//
//import java.io.*;
//import java.util.logging.Level;
//
///**
// * A simple example to introduce building forms. As your real application is
// * probably much more complicated than this example, you could re-use this form in
// * multiple places. This example component is only used in VaadinUI.
// * <p>
// * In a real world application you'll most likely using a common super class for all your
// * forms - less code, better UX. See e.g. AbstractForm in Virin
// * (https://vaadin.com/addon/viritin).
// */
//@SpringComponent
//@UIScope
//public class QuoteEditor extends VerticalLayout {
//
//    private final QuoteRepository repository;
//
//    /**
//     * The currently edited customer
//     */
//    private QuoteUIModel quote;
//
//    /* Fields to edit properties in Customer entity */
//    TextField name = new TextField("Name");
//    TextField description = new TextField("Description");
//    ImageUploader receiver = new ImageUploader();
//    Upload image = new Upload("image",receiver);
//
//    /* Action buttons */
//    Button save = new Button("Save", FontAwesome.SAVE);
//    Button cancel = new Button("Cancel");
//    Button delete = new Button("Delete", FontAwesome.TRASH_O);
//    CssLayout actions = new CssLayout(save, cancel, delete);
//
//    @Autowired
//    public QuoteEditor(QuoteRepository repository) {
//        this.repository = repository;
//
//        addComponents(name, description,image, actions);
//
//        // Configure and style components
//        setSpacing(true);
//        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
//        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
//        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
//
//        // wire action buttons to save, delete and reset
//        //save.addClickListener(e -> repository.save(quote));
//        save.addClickListener(receiver);
//
//        delete.addClickListener(e -> repository.delete(quote));
//        cancel.addClickListener(e -> editCustomer(quote));
//        setVisible(false);
//    }
//
//    class ImageUploader implements Upload.Receiver, Upload.SucceededListener,Button.ClickListener  {
//        public File file;
//
//        public OutputStream receiveUpload(String filename,
//                                          String mimeType) {
//            // Create upload stream
//            FileOutputStream fos = null; // Stream to write to
//            try {
//                // Open the file for writing.
//                file = new File("/tmp/uploads/" + filename);
//                fos = new FileOutputStream(file);
//                FileInputStream fis = new FileInputStream(file);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                byte[] buf = new byte[1024];
//                try {
//                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                        bos.write(buf, 0, readNum); //no doubt here is 0
//                        //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
//                        System.out.println("read " + readNum + " bytes,");
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                byte[] bytes = bos.toByteArray();
//                quote.setImage(bytes);
//                repository.save(quote);
//            } catch (final java.io.FileNotFoundException e) {
//                new Notification("Could not open file<br/>",
//                        e.getMessage(),
//                        Notification.Type.ERROR_MESSAGE)
//                        .show(Page.getCurrent());
//                return null;
//            }
//            return fos; // Return the output stream to write to
//        }
//
//        public void buttonClick(Button.ClickEvent event){
//            repository.save(quote);
//        }
//
//        public void uploadSucceeded(Upload.SucceededEvent event) {
//            // Show the uploaded file in the image viewer
////            image.setVisible(true);
////            image.setSource(new FileResource(file));
//        }
//    };
//
//    public interface ChangeHandler {
//
//        void onChange();
//    }
//
//    public final void editCustomer(QuoteUIModel c) {
//        final boolean persisted = c.getId() != null;
//        if (persisted) {
//            // Find fresh entity for editing
//            quote = repository.findOne(c.getId());
//        }
//        else {
//            quote = c;
//        }
//        cancel.setVisible(persisted);
//
//        // Bind customer properties to similarly named fields
//        // Could also use annotation or "manual binding" or programmatically
//        // moving values from fields to entities before saving
//        BeanFieldGroup.bindFieldsUnbuffered(quote, this);
//
//        setVisible(true);
//
//        // A hack to ensure the whole form is visible
//        save.focus();
//        // Select all text in firstName field automatically
//        name.selectAll();
//    }
//
//    public void setChangeHandler(ChangeHandler h) {
//        // ChangeHandler is notified when either save or delete
//        // is clicked
//        save.addClickListener(e -> h.onChange());
//        delete.addClickListener(e -> h.onChange());
//    }
//
//}