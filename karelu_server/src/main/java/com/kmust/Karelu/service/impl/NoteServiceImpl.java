package com.kmust.Karelu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmust.Karelu.entity.*;
import com.kmust.Karelu.mapper.NoteMapper;
import com.kmust.Karelu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-17
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private INoteService noteService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodService goodService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IFocusService focusService;

    @Override
    public RespBean doOutput(NoteParam noteParam) {
        String[] upload = noteParam.getImagesUrl().split(";");
        String[] del = noteParam.getDeleteImg().split(";");
        for(int i = 0 ; i < del.length ; i ++){
            for(int j = 0 ; j < upload.length ; j ++){
                if(del[i].equals(upload[j])){
                    upload[j] = "del";
                    break;
                }
            }
        }
        String img = "";
        for(int i = 0 ; i < upload.length ; i ++){
            if(upload[i].equals("del")){
                continue;
            }else{
                img += upload[i] + ";";
            }
        }
        System.out.println(img);
        Note note = new Note();
        note.setImages(img);
        note.setNote(noteParam.getNote());
        note.setUserid(noteParam.getUserid());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        note.setCreateDate(date);
        noteMapper.insert(note);
        return RespBean.sucess("");
    }

    @Override
    public RespPageBean getNotesByPage(Integer currentPage, Integer size,Long id) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list();
        int begin = totalNotes.size() - 1;
        begin -= (currentPage-1)*size;
        List<NoteShow> noteShows = new ArrayList<>();

        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));

        for(int i = begin; i > begin - size && i >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            System.out.println(user);
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            tempNS.setNote(totalNotes.get(i).getNote());
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            tempNS.setDate(totalNotes.get(i).getCreateDate());
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments
                 ) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                String head = "";
                if (a.getFatherid() != 0){
                    Comment comment = commentService.getById(a.getFatherid());
                    User user2 = userService.getById(comment.getUserid());
                    head = "回复 " + " " + user2.getNickName() + " :";
                }
                c.setCommentthing(head + a.getCommentthing());
                c.setCommentid(a.getCommentid());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-round");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
             ) {
            for (Good b:goods
                 ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }
        RespPageBean res = new RespPageBean((long)totalNotes.size(),noteShows);
        return res;
    }

    @Override
    public RespPageBean getNotesByPageByHot(Integer currentPage, Integer size, Long id) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list();
        int begin = totalNotes.size() - 1;
        List<NoteShow> noteShows = new ArrayList<>();

        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));

        for(int i = begin;i >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            System.out.println(user);
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            tempNS.setNote(totalNotes.get(i).getNote());
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(d);
            if(totalNotes.get(i).getCreateDate().substring(0,10).equals(date.substring(0,10))){
                tempNS.setDate(totalNotes.get(i).getCreateDate());
            }else{
                continue;
            }
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments
            ) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                c.setCommentid(a.getCommentid());
                String head = "";
                if (a.getFatherid() != 0){
                    Comment comment = commentService.getById(a.getFatherid());
                    User user2 = userService.getById(comment.getUserid());
                    head = "回复 " + " " + user2.getNickName() + " :";
                }
                c.setCommentthing(head + a.getCommentthing());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-round");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
        ) {
            for (Good b:goods
            ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }
        Collections.sort(noteShows, new Comparator<NoteShow>() {
            @Override
            public int compare(NoteShow o1, NoteShow o2) {
                return (o2.getGoodcount() + o2.getCommentCount()) - (o1.getGoodcount() + o1.getCommentCount());
            }
        });
        List<NoteShow> q = new ArrayList<>();
        for(int i = (currentPage-1)*10; i < (currentPage)*10 && i < noteShows.size(); i ++){
            q.add(noteShows.get(i));
        }
        RespPageBean res = new RespPageBean((long)noteShows.size(),q);
        return res;
    }

    @Override
    public RespPageBean getFocusNotesByPage(Integer currentPage, Integer size, Long id) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list();
        int begin = totalNotes.size() - 1;
        List<NoteShow> noteShows = new ArrayList<>();

        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));

        for(int i = begin; i  >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            System.out.println(user);
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            tempNS.setNote(totalNotes.get(i).getNote());
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            tempNS.setDate(totalNotes.get(i).getCreateDate());
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments
            ) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                c.setCommentid(a.getCommentid());
                String head = "";
                if (a.getFatherid() != 0){
                    Comment comment = commentService.getById(a.getFatherid());
                    User user2 = userService.getById(comment.getUserid());
                    head = "回复 " + " " + user2.getNickName() + " :";
                }
                c.setCommentthing(head + a.getCommentthing());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-round");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
        ) {
            for (Good b:goods
            ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }

        List<NoteShow> finalNoteShow = new ArrayList<>();
        List<Focus> foci = focusService.list(new QueryWrapper<Focus>().eq("userid",uid));
        for (NoteShow a: noteShows){
            for (Focus f:foci
                 ) {
                User useraaa = userService.getOne(new QueryWrapper<User>().eq("nickname",a.getUsername()));
                if (f.getFocusedid().equals(new Integer(useraaa.getId().toString()))){
                    finalNoteShow.add(a);
                }
            }
        }
        List<NoteShow> q = new ArrayList<>();
        for(int i = (currentPage-1)*10; i < (currentPage)*10  && i < finalNoteShow.size(); i ++){
            q.add(finalNoteShow.get(i));
        }
        RespPageBean res = new RespPageBean((long)finalNoteShow.size(),q);
        return res;
    }

    @Override
    public RespPageBean getTargetNewNotesByPage(Integer currentPage, Integer size, Long id, String target) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list();
        int begin = totalNotes.size() - 1;
        begin -= (currentPage-1)*size;
        List<NoteShow> noteShows = new ArrayList<>();

        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));

        for(int i = begin; i >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            System.out.println(user);
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            if(totalNotes.get(i).getNote().contains(target)){
                tempNS.setNote(totalNotes.get(i).getNote());
            }else{
                continue;
            }
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            tempNS.setDate(totalNotes.get(i).getCreateDate());
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                String head = "";
                if (a.getFatherid() != 0){
                    Comment comment = commentService.getById(a.getFatherid());
                    User user2 = userService.getById(comment.getUserid());
                    head = "回复 " + " " + user2.getNickName() + " :";
                }
                c.setCommentid(a.getCommentid());
                c.setCommentthing(head + a.getCommentthing());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-round");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
        ) {
            for (Good b:goods
            ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }
        RespPageBean res = new RespPageBean((long)noteShows.size(),noteShows);
        return res;
    }

    @Override
    public RespPageBean getTargetHotNotesByPage(Integer currentPage, Integer size, Long id, String target) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list();
        int begin = totalNotes.size() - 1;
        List<NoteShow> noteShows = new ArrayList<>();

        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));

        for(int i = begin;i >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            System.out.println(user);
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            if(totalNotes.get(i).getNote().contains(target)){
                tempNS.setNote(totalNotes.get(i).getNote());
            }else{
                continue;
            }
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            tempNS.setDate(totalNotes.get(i).getCreateDate());
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments
            ) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                c.setCommentid(a.getCommentid());
                c.setCommentthing(a.getCommentthing());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-square");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
        ) {
            for (Good b:goods
            ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }
        Collections.sort(noteShows, new Comparator<NoteShow>() {
            @Override
            public int compare(NoteShow o1, NoteShow o2) {
                return (o2.getGoodcount() + o2.getCommentCount()) - (o1.getGoodcount() + o1.getCommentCount());
            }
        });

        RespPageBean res = new RespPageBean((long)noteShows.size(),noteShows);
        return res;
    }

    @Override
    public RespPageBean getMyNotes(Integer currentPage, Integer size, Long id, String target) {
        Integer uid = new Integer(id.toString());

        List<Note> totalNotes = noteService.list(new QueryWrapper<Note>().eq("userid",uid));
        List<NoteShow> noteShows = new ArrayList<>();
        List<Good> goods = goodService.list(new QueryWrapper<Good>().eq("userid",uid));
        for(int i = totalNotes.size()-1; i >= 0; i --){
            User user = userService.getById(totalNotes.get(i).getUserid());
            NoteShow tempNS = new NoteShow();
            tempNS.setIcon(user.getIcon());
            tempNS.setUsername(user.getNickName());
            tempNS.setImages(totalNotes.get(i).getImages().split(";"));
            tempNS.setNote(totalNotes.get(i).getNote());
            tempNS.setNoteid(totalNotes.get(i).getNoteid());
            tempNS.setDate(totalNotes.get(i).getCreateDate());
            List<Good> goodCount = goodService.list(new QueryWrapper<Good>().eq("noteid",tempNS.getNoteid()));
            List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("noteid",tempNS.getNoteid()));
            List<CommentShow> commentShows = new ArrayList<>();
            for (Comment a: comments
            ) {
                User user1 = userService.getById(a.getUserid());
                CommentShow c = new CommentShow();
                String head = "";
                if (a.getFatherid() != 0){
                    Comment comment = commentService.getById(a.getFatherid());
                    User user2 = userService.getById(comment.getUserid());
                    head = "回复 " + " " + user2.getNickName() + " :";
                }
                c.setCommentthing(head + a.getCommentthing());
                c.setCommentid(a.getCommentid());
                c.setCreatedate(a.getCreatedate());
                c.setNoteid(a.getNoteid());
                c.setUserid(a.getUserid());
                c.setIconrul(user1.getIcon());
                c.setNickname(user1.getNickName());
                c.setSeeReply("el-icon-chat-line-round");
                commentShows.add(c);
            }
            tempNS.setComment(commentShows);
            tempNS.setCommentCount(tempNS.getComment().size());
            tempNS.setGoodcount(goodCount.size());
            tempNS.setBeGooded("");
            tempNS.setCommentChoice("el-icon-chat-dot-round");
            noteShows.add(tempNS);
        }
        for (NoteShow a:noteShows
        ) {
            for (Good b:goods
            ) {
                if (a.getNoteid().equals(b.getNoteid())){
                    a.setBeGooded("danger");
                }
            }
        }
        RespPageBean res = new RespPageBean((long)totalNotes.size(),noteShows);
        return res;
    }
}
