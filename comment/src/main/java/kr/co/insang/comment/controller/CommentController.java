package kr.co.insang.comment.controller;

import kr.co.insang.comment.dto.CommentDTO;
import kr.co.insang.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{userid}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUserID(@PathVariable("userid") String userid){
        List<CommentDTO> comments = commentService.getCommentByUserid(userid);
        if(comments!=null)
            return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);

        return new ResponseEntity<List<CommentDTO>>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> getCommentsByVideoID(@RequestParam(value = "videoid") Long videoid){
        List<CommentDTO> comments = commentService.getCommentByVideoID(videoid);
        if(comments!=null)
            return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);

        return new ResponseEntity<List<CommentDTO>>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<Long> createComment(@RequestBody CommentDTO comment){
        if(commentService.saveComment(comment))
            return new ResponseEntity<Long>(HttpStatus.OK);

        return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("")
    public ResponseEntity<Long> updateComment(@RequestBody CommentDTO comment){
        if(commentService.updateComment(comment))
            return new ResponseEntity<Long>(HttpStatus.OK);

        return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{commentid}")
    public ResponseEntity<Void> deleteCommentByCommentID(@PathVariable("commentid") Long commentid){
        if(commentService.deleteCommentByCommenID(commentid)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("")
    public ResponseEntity<Void> deleteCommentByUserID(@RequestParam(required = false, value = "userid") String userid, @RequestParam(required = false, value = "videoid") Long videoid){
        if(userid != null ^ videoid != null) {  //xor 둘이 결과가 다를때만 Ok.
            if (userid != null) {
                if (commentService.deleteCommentByUserID(userid)) {
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
            } else {
                if (commentService.deleteCommentByVideoID(videoid)) {
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }


}
