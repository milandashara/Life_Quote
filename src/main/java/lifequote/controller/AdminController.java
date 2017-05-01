package lifequote.controller;

import lifequote.domain.*;
import lifequote.model.QuoteBrowserUIModel;
import lifequote.model.QuoteUIModel;
import lifequote.storage.StorageFileNotFoundException;
import lifequote.storage.StorageService;
import lifequote.utility.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by milanashara on 12/30/16.
 */

@Controller
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    VirtueRepository virtueRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private  CounterService counterService;


    @PostMapping("/admin/quote")
    public String quoteSubmit(@RequestParam("file") MultipartFile file, @ModelAttribute QuoteUIModel quote, final RedirectAttributes redirectAttributes) {
        lifequote.domain.Quote quote1 = new lifequote.domain.Quote();
        quote1.setDescription(quote.getDescription());
        quote1.setName(quote.getName());
        //quote1.setImageRelativeUrl(quote.getImageRelativeUrl());

        if (quote.getId() != null){
            quote1 = quoteRepository.findOne(quote.getId());
//            quote1.setId(quote.getId());
        }
        if(quote.getVirtueId() != null)
        {
            quote1.setVirtue(virtueRepository.findOne(quote.getVirtueId()));
        }

        if (quote.getAuthorId() != null){
            quote1.setAuthor(authorRepository.findOne(quote.getAuthorId()));
        }

        if (quote.getVirtue() != null){
            quote1.setVirtue(virtueRepository.findOne(quote.getVirtue().getId()));
        }
        if (quote.getAuthor() != null){
            quote1.setAuthor(authorRepository.findOne(quote.getAuthor().getId()));
        }

        if (file != null && !file.isEmpty() ){
            //String imageFileName=quote1.getName()+quote1.getAuthor().getName()+ quote1.getVirtue().getName();
            String imageFileName= UUID.randomUUID().toString().replace("-","")+"."+file.getOriginalFilename().split("\\.")[1];
            storageService.store(file,imageFileName);
            quote1.setImageRelativeUrl("/quote_images/"+imageFileName);
        }

        if (quoteRepository.save(quote1)!=null){
            redirectAttributes.addFlashAttribute("status", "success");
        }else {
            redirectAttributes.addFlashAttribute("status", "unsuccess");
        }


        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());


        return "redirect:/admin/quote";
    }

    @GetMapping("/quote_images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        log.info("get file:" + filename);
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

//


    @GetMapping("/")
    public String getQuoteBrowser(@ModelAttribute QuoteBrowserUIModel quoteBrowserUIModel, Model model,  Pageable pageable,@RequestParam(value = "page.page",defaultValue = "1") Integer pageNum,@RequestParam(value = "page.size",defaultValue = "20") Integer pageSize,@RequestParam(value = "virtue_id",defaultValue = "-1") Long virtue_id) {


        if (pageNum != null && pageSize != null){
            if (pageNum > 0)
                pageable = new PageRequest(pageNum-1,pageSize);
        }

//        if (quoteBrowserUIModel == null) {
//            quoteBrowserUIModel = new QuoteBrowserUIModel();
//            quoteBrowserUIModel.setPageNum(pageNum);
//            quoteBrowserUIModel.setPageSize(pageSize);
//        }



       // pageable = new PageRequest(quoteBrowserUIModel.getPageNum()-1,quoteBrowserUIModel.getPageSize());


        Page<Quote> quotePage = null;

        if (virtue_id == -1) {
            quotePage = quoteRepository.findAll(pageable);
        }
        else {
            quotePage = quoteRepository.findByVirtue_Id(virtue_id,pageable);
        }

        if (quotePage != null) {
            quoteBrowserUIModel.setTotalElement(quotePage.getTotalElements());

            List<QuoteUIModel> quoteUIModelList = new ArrayList<>();
            for (Quote quote : quotePage.getContent()) {
                QuoteUIModel quoteUIModel = new QuoteUIModel();
                quoteUIModel.setImageRelativeUrl(quote.getImageRelativeUrl());
                quoteUIModel.setDescription(quote.getDescription());
                quoteUIModelList.add(quoteUIModel);
            }

            quoteBrowserUIModel.setQuoteUIModelList(quoteUIModelList);
            model.addAttribute("quoteBrowser", quoteBrowserUIModel);
            PageWrapper<lifequote.domain.Quote> page = new PageWrapper<lifequote.domain.Quote>
                    (quotePage, "/");
            model.addAttribute("page", page);
            if (virtue_id != -1)
                model.addAttribute("virtue_id",virtue_id);
        }

        List<Virtue> virtues = virtueRepository.findAll();
        model.addAttribute("virtues",virtues);

        log.info("getQuoteBrowser: "+quoteBrowserUIModel);

        //if (page.getNumber() == 1)
            return "index";
        //else
          //  return "index_quote";
    }


    @GetMapping("/admin/quote/add")
    public String getquoteSubmit(@ModelAttribute QuoteUIModel editQuote, Model model) {

        editQuote.setAuthors(authorRepository.findAll());
        editQuote.setVirtues(virtueRepository.findAll());
        model.addAttribute("editQuote",editQuote);

        return "quote/submitQuote";
    }

    @GetMapping("/admin/virtue/add")
    public String getVirturAddPage(@ModelAttribute Virtue virtue,Model model) {

        model.addAttribute("editVirtue",virtue);

        return "virtue/editPage";
    }

    @GetMapping("/admin/author/add")
    public String getAuthorAddPage(@ModelAttribute Author author,Model model) {

        model.addAttribute("editAuthor",author);

        return "author/editPage";
    }

    @GetMapping("/admin")
    public String goToHome() {
        return "redirect:/admin/quote";
    }

    @GetMapping("/login")
    public ModelAndView getLogin(@ModelAttribute QuoteUIModel quote) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

    /* The user is logged in :) */
            return new ModelAndView("redirect:/admin/quote");
        }else {
            return new ModelAndView("login");
        }
    }

    @RequestMapping(value = {"/admin/virtue","/admin/virtue/savepage"}, method = RequestMethod.GET)
    public String savePage(Model model) {
        model.addAttribute("virtue", new Virtue());
        model.addAttribute("virtues", (ArrayList<Virtue>)virtueRepository.findAll());
        return "virtue/index";
    }

    @RequestMapping(value = {"/admin/quote","/admin/quote/savepage"}, method = RequestMethod.GET)
    public String saveQuote(Model model,  Pageable pageable,@RequestParam(value = "page.page",required = false) Integer pageNum,@RequestParam(value = "page.size",required = false) Integer pageSize) {
        model.addAttribute("quote", new QuoteUIModel());
        if (pageNum != null && pageSize != null){
            if (pageNum > 0)
                pageable = new PageRequest(pageNum-1,pageSize);
        }
        Page<lifequote.domain.Quote> quotePage = quoteRepository.findAll(pageable);
        PageWrapper<lifequote.domain.Quote> page = new PageWrapper<lifequote.domain.Quote>
                (quotePage, "/admin/quote");
        model.addAttribute("page",page);
        model.addAttribute("quotes", quotePage.getContent());
        return "quote/index";
    }

    @RequestMapping(value = {"/admin/virtue/save"}, method = RequestMethod.POST)
    public String saveVirtue(@ModelAttribute("virtue") Virtue virtue,
                               final RedirectAttributes redirectAttributes) {

        if(virtueRepository.save(virtue)!=null) {
            redirectAttributes.addFlashAttribute("status", "success");
        } else {
            redirectAttributes.addFlashAttribute("status", "unsuccess");
        }
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());

        return "redirect:/admin/virtue/savepage";
    }

    @RequestMapping(value = "/admin/virtue/{operation}/{id}", method = RequestMethod.GET)
    public String editRemoveVirtue(@PathVariable("operation") String operation,
                                     @PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
                                     Model model) {
        if(operation.equals("delete")) {
            try {
                virtueRepository.delete(id);
                redirectAttributes.addFlashAttribute("status", "success");
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("status", "unsuccess");
            }
        } else if(operation.equals("edit")){
            Virtue virtue = virtueRepository.findOne(id);
            if(virtue!=null) {
                model.addAttribute("editVirtue", virtue);
                return "virtue/editPage";
            } else {
                redirectAttributes.addFlashAttribute("status","notfound");
            }
        }

        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());

        return "redirect:/admin/virtue/savepage";
    }

    @RequestMapping(value = "/admin/virtue/update", method = RequestMethod.POST)
    public String updateEmployee(@ModelAttribute("editVirtue") Virtue virtue,
                                 final RedirectAttributes redirectAttributes) {
        if(virtueRepository.save(virtue)!=null) {
            redirectAttributes.addFlashAttribute("status", "success");
        } else {
            redirectAttributes.addFlashAttribute("status", "unsuccess");
        }
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
        return "redirect:/admin/virtue";
    }


    @RequestMapping(value = "/admin/quote/{operation}/{id}", method = RequestMethod.GET)
    public String editRemoveQuote(@PathVariable("operation") String operation,
                                     @PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
                                     Model model) {
        if(operation.equals("delete")) {
            try {
                quoteRepository.delete(id);
                redirectAttributes.addFlashAttribute("status", "success");
            }catch (Exception e){
                log.error(e.getMessage());
                redirectAttributes.addFlashAttribute("status", "unsuccess");
            }
        } else if(operation.equals("edit")){
            lifequote.domain.Quote quote = quoteRepository.findOne(id);
            if(quote!=null) {
                QuoteUIModel quote1 = new QuoteUIModel();
                quote1.setAuthors(authorRepository.findAll());
                quote1.setVirtues(virtueRepository.findAll());
                quote1.setDescription(quote.getDescription());
                quote1.setAuthorId(quote.getId());
                quote1.setImageRelativeUrl(quote.getImageRelativeUrl());
                quote1.setName(quote.getName());
                quote1.setAuthor(quote.getAuthor());
                quote1.setVirtue(quote.getVirtue());
                quote1.setId(quote.getId());
                model.addAttribute("editQuote", quote1);

                return "quote/submitQuote";
            } else {
                redirectAttributes.addFlashAttribute("status","notfound");
            }
        }

        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());

        return "redirect:/admin/quote";
    }



    @RequestMapping(value = {"/admin/author","/admin/author/savepage"}, method = RequestMethod.GET)
    public String saveAuthorPage(Model model) {
        model.addAttribute("author", new Virtue());
        model.addAttribute("authors", (ArrayList<Author>)authorRepository.findAll());
        return "author/index";
    }

    @RequestMapping(value = {"/admin/author/save"}, method = RequestMethod.POST)
    public String saveAuthor(@ModelAttribute("author") Author author,
                               final RedirectAttributes redirectAttributes) {

        if(authorRepository.save(author)!=null) {
            redirectAttributes.addFlashAttribute("status", "success");
        } else {
            redirectAttributes.addFlashAttribute("status", "unsuccess");
        }
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
        return "redirect:/admin/author/savepage";
    }

    @RequestMapping(value = "/admin/author/{operation}/{id}", method = RequestMethod.GET)
    public String editRemoveAuthor(@PathVariable("operation") String operation,
                                     @PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
                                     Model model) {
        if(operation.equals("delete")) {
            try {
                authorRepository.delete(id);
                redirectAttributes.addFlashAttribute("status", "success");
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("status", "unsuccess");
            }
        } else if(operation.equals("edit")){
            Author author = authorRepository.findOne(id);
            if(author!=null) {
                model.addAttribute("editAuthor", author);
                return "author/editPage";
            } else {
                redirectAttributes.addFlashAttribute("status","notfound");
            }
        }
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());

        return "redirect:/admin/author/savepage";
    }

    @RequestMapping(value = "/admin/author/update", method = RequestMethod.POST)
    public String updateAuthor(@ModelAttribute("editAuthor") Author author,
                                 final RedirectAttributes redirectAttributes) {
        if(authorRepository.save(author)!=null) {
            redirectAttributes.addFlashAttribute("status", "success");
        } else {
            redirectAttributes.addFlashAttribute("status", "unsuccess");
        }
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
        return "redirect:/admin/author/savepage";
    }




    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleAny(Throwable exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exc.getMessage());
    }
}
