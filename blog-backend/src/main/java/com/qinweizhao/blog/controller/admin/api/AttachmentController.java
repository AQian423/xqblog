package com.qinweizhao.blog.controller.admin.api;

import com.qinweizhao.blog.model.convert.AttachmentConvert;
import com.qinweizhao.blog.model.core.PageResult;
import com.qinweizhao.blog.model.dto.AttachmentDTO;
import com.qinweizhao.blog.model.entity.Attachment;
import com.qinweizhao.blog.model.enums.AttachmentType;
import com.qinweizhao.blog.model.param.AttachmentParam;
import com.qinweizhao.blog.model.param.AttachmentQueryParam;
import com.qinweizhao.blog.service.AttachmentService;
import com.qinweizhao.blog.util.ResultUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 附件
 *
 * @author johnniang
 * @author qinweizhao
 * @date 2019-03-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    /**
     * 分页
     *
     * @param param parame
     * @return Page
     */
    @GetMapping
    public PageResult<AttachmentDTO> page(AttachmentQueryParam param) {
        return attachmentService.page(param);
    }

    /**
     * 列出所有媒体类型
     *
     * @return List
     */
    @GetMapping("media_types")
    public List<String> listMediaTypes() {
        System.out.println("请求已经收到");
        return attachmentService.listMediaType();
    }

    /**
     * 列出所有类型
     *
     * @return List
     */
    @GetMapping("types")
    public List<AttachmentType> listTypes() {
        return attachmentService.listAllType();
    }



    /**
     * 更新附件
     *
     * @param id    id
     * @param param param
     * @return AttachmentDTO
     */
    @PutMapping("{attachmentId:\\d+}")
    public Boolean updateBy(@PathVariable("attachmentId") Integer id,
                                  @RequestBody @Valid AttachmentParam param) {

        return attachmentService.updateById(id,param);
    }



    /**
     * 删除附件
     *
     * @param id id
     * @return AttachmentDTO
     */
    @DeleteMapping("{id:\\d+}")
    public Boolean deletePermanently(@PathVariable("id") Integer id) {
        return attachmentService.removeById(id);
    }


    /**
     * 批量永久删除附件
     *
     * @param ids ids
     * @return Boolean
     */
    @DeleteMapping
    public Boolean deletePermanentlyInBatch(@RequestBody List<Integer> ids) {
        return attachmentService.removeByIds(ids);
    }

    /**
     * 上传单个文件
     *
     * @param file file
     * @return AttachmentDTO
     */
    @PostMapping("upload")
    public Boolean uploadAttachment(@RequestPart("file") MultipartFile file) {
        return attachmentService.upload(file);
    }


    /**
     * 上传多个文件
     *
     * @param files files
     * @return List<AttachmentDTO>
     */
    @PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean uploadAttachments(@RequestPart("files") MultipartFile[] files) {

        for (MultipartFile file : files) {
            attachmentService.upload(file);
        }

        return true;
    }


}
