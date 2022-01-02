package com.blogfrwk.apiblogfrwk.service;

import com.blogfrwk.apiblogfrwk.dto.mapper.CommentMapper;
import com.blogfrwk.apiblogfrwk.dto.request.CommentDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Comment;
import com.blogfrwk.apiblogfrwk.exception.CommentCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.CommentCanNotBeUpdatedException;
import com.blogfrwk.apiblogfrwk.exception.CommentNotFoundException;
import com.blogfrwk.apiblogfrwk.repository.CommentRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {

    private CommentRepository commentRepository;
    private JwtUtils jwtUtils;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

    public MessageResponse createComment(CommentDTO commentDTO) {
        Comment commentToSave = commentMapper.toModel(commentDTO);
        commentToSave.setOwnerName(getUserCurrentSection());
        commentToSave.setCreationDate(sdf.format(new Date()));
        Comment savedComment = commentRepository.save(commentToSave);
        return new MessageResponse("Created Comment with ID " + savedComment.getId());
    }

    public List<CommentDTO> listAll() {
        List<Comment> allComments = commentRepository.findAll();
        return allComments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO findByID(Long id) throws CommentNotFoundException {
        Comment comment = verifyExists(id);
        return commentMapper.toDTO(comment);
    }

    public MessageResponse updateById(Long id, CommentDTO commentDTO) throws CommentNotFoundException, CommentCanNotBeUpdatedException {
        Comment currentComment = verifyExists(id);
        if(!isCurrentUserOwnsOfTheComment(currentComment)) {
            throw new CommentCanNotBeUpdatedException();
        }
        commentDTO.setId(id);
        Comment commentToUpdate = commentMapper.toModel(commentDTO);
        commentToUpdate.setOwnerName(getUserCurrentSection());
        commentToUpdate.setCreationDate(currentComment.getCreationDate());
        commentRepository.save(commentToUpdate);
        return new MessageResponse("Updated comment with ID " + id);
    }

    public MessageResponse deleteById(Long id) throws CommentNotFoundException, CommentCanNotBeDeletedException {
        Comment currentComment = verifyExists(id);
        if(!isCurrentUserOwnsOfTheComment(currentComment)) {
            throw new CommentCanNotBeDeletedException();
        }
        commentRepository.deleteById(id);
        return new MessageResponse("Comment with ID " + id + " has been deleted successfully");
    }

    private Comment verifyExists(Long id) throws CommentNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new CommentNotFoundException(id);
        }
        return optionalComment.get();
    }

    private String getUserCurrentSection() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    public boolean isCurrentUserOwnsOfTheComment(Comment comment) {
        String userCurrentSection = getUserCurrentSection();
        if (comment.getOwnerName() != null && comment.getOwnerName().equals(userCurrentSection)) {
            return true;
        }
        return false;
    }
}
