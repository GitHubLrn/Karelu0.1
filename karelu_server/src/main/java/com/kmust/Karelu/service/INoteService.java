package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.NoteParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.RespPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-17
 */
public interface INoteService extends IService<Note> {

    RespBean doOutput(NoteParam noteParam);

    RespPageBean getNotesByPage(Integer currentPage, Integer size,Long id);

    RespPageBean getNotesByPageByHot(Integer currentPage, Integer size, Long id);

    RespPageBean getFocusNotesByPage(Integer currentPage, Integer size, Long id);

    RespPageBean getTargetNewNotesByPage(Integer currentPage, Integer size, Long id, String target);

    RespPageBean getTargetHotNotesByPage(Integer currentPage, Integer size, Long id, String target);

    RespPageBean getMyNotes(Integer currentPage, Integer size, Long id, String target);
}
