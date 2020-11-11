package com.lazyhippos.todolistapp.application.controller;

import com.lazyhippos.todolistapp.domain.model.Articles;
import com.lazyhippos.todolistapp.domain.model.Topics;
import com.lazyhippos.todolistapp.domain.service.ArticleService;
import com.lazyhippos.todolistapp.domain.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final String INDEX_VIEW = "index";

    public ArticleController (ArticleService articleService, TopicService topicService){
        this.articleService = articleService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String showHomePage (Model model) {
        // Fetch all articles which is available
        List<Articles> articles = articleService.retrieveAll();
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Set to Model
        model.addAttribute("articles", articles);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", "Recommendation");
        // Dispatch Home page
        return INDEX_VIEW;
    }

    @GetMapping("/categories/{topicId}")
    public String showCategoryPage (@PathVariable("topicId") String topicId, Model model) {
        // Fetch all by Topic ID
        List<Articles> articles = articleService.retrieveByTopicId(topicId);
        // Fetch all topics which is available
        List<Topics> topics = topicService.retrieveAll();
        // Map Topic Name and ID Pairs
        Map<String,String> topicMap = topics.stream().collect
                (Collectors.toMap(Topics::getTopicId, Topics::getTopicName));
        // Set to Model
        model.addAttribute("articles", articles);
        model.addAttribute("topics", topics);
        model.addAttribute("activeCategoryName", topicMap.get(topicId));
        // Dispatch Home page
        return INDEX_VIEW;
    }


//    @GetMapping("/")
//    public String showHome(@RequestParam(required = false) String label_id,
//                           @RequestParam(required = false, defaultValue = "asc") String sort,
//                           @RequestParam(defaultValue = "false") Boolean isError,
//                           Principal principal, Model model
//                           ){
//
//        final String INDEX_VIEW = "index";
//        final String LABEL_STRING_ALL = "all";
//
//        String loginUserID = principal.getName();
//        String listName = "Recommendation";
//        List<Todos> todos;
//
//        if(label_id == null || label_id.equals(LABEL_STRING_ALL)){
//            todos = todoService.retrieveAll(loginUserID, sort);
//        } else {
//            // Retrieve all To-do by Label ID
//            List<TodoLabel> todoLabelList = todoLabelService.retrieveAllByLabelId(label_id);
//            List<String> todoIdList = retrieveTodoIds(todoLabelList);
//            todos = todoService.retrieveByTodoIdList(todoIdList, sort);
//        }
//        // Remove Completed tasks
//        List<Todos> incompleteTasks = todos.stream()
//                .filter(t -> !t.getIsCompleted())
//                .collect(Collectors.toList());
//        // Retrieve All Labels that the user has
//        List<Labels> labelsList = labelService.retrieveAll(loginUserID);
//        for (Labels label: labelsList) {
//            if(label.getLabelId().equals(label_id)) {
//                listName = label.getLabelName();
//            }
//        }
//        model.addAttribute("todos", incompleteTasks);
//        model.addAttribute("labels", labelsList);
//        model.addAttribute("todoForm", new TodoRequest());
//        model.addAttribute("listName", listName);
//        // Show Error message
//        if(isError){
//            model.addAttribute("errorMessage", "Something wrong");
//        }
//
//        return INDEX_VIEW;
//    }
//
//    @GetMapping("/{todoId}/edit")
//    public String showTodoEdit(@PathVariable("todoId") String todoId,
//                                 @RequestParam(defaultValue = "false") Boolean isError,
//                                 Model model){
//        final String TODO_EDIT_VIEW = "todoEdit";
//        // Retrieve the to-do by ID
//        Todos todo= todoService.retrieveOne(todoId);
//        // Retrieve all labels that login user created
//        List<Labels> allLabels = labelService.retrieveAll(todo.getUserId());
//        // Generate To-doLabel Request
//        TodoLabelRequest todoLabelRequest = new TodoLabelRequest(
//                allLabels,
//                todoId
//        );
//        // Fetch all Related LabelIDs
//        List<String> labelIdList = todoLabelService.retrieveLabelIds(todoId);
//        // Retrieve All Related Label Objects
//        List<Labels> relatedLabels = labelService.retrieveByLabelIds(labelIdList);
//        model.addAttribute("todoRequest", TodoRequest.generateTodoRequest(todo));
//        model.addAttribute("relatedLabels", relatedLabels);
//        model.addAttribute("todoLabelRequest", todoLabelRequest);
//        if(isError){
//            model.addAttribute("hasError", "Try Again");
//        }
//        return TODO_EDIT_VIEW;
//    }

//    @GetMapping("/delete/{todoId}")
//    public String deleteTodo(@PathVariable("todoId") String todoId){
//        todoService.delete(todoId);
//        return "redirect:/to-do/list";
//    }

//    @PostMapping("/register")
//    public String registerTodo (Principal principal,
//                                @Valid @ModelAttribute(name = "todoForm") TodoRequest todoForm,
//                                BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            String queryParameter = "?isError=true";
//            return "redirect:/to-do/list" + queryParameter;
//        }
//        // Fetch current datetime
//        LocalDateTime currentDatetime = LocalDateTime.now();
//        String loginUserId = principal.getName();
//        // Store new object
//        todoService.store(todoForm.getTitle(), currentDatetime, loginUserId);
//        return "redirect:/to-do/list";
//    }

//    @PostMapping("/update/{todoId}")
//    public String updateTodo(@PathVariable("todoId") String todoId,
//                             @Valid @ModelAttribute TodoRequest request,
//                             BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "redirect:/to-do/" + todoId + "/edit"+ "?isError=true";
//        }
//        // Fetch current datetime
//        LocalDateTime currentDatetime = LocalDateTime.now();
//        // Update
//        todoService.update(todoId, request, currentDatetime);
//        return "redirect:/to-do/list";
//    }

//    @PostMapping("/complete/{todoId}")
//    public String completeTodo(@PathVariable("todoId") String todoId){
//        todoService.complete(todoId);
//        return "redirect:/to-do/list";
//    }
//
//    static List<String> retrieveTodoIds(List<TodoLabel> todoLabelList){
//        List<String> todoIds = new ArrayList<>();
//        if(todoLabelList != null) {
//            todoLabelList.forEach(
//                    todoLabel -> todoIds
//                            .add(todoLabel.getTodoId())
//            );
//        }
//        return todoIds;
//    }

}
