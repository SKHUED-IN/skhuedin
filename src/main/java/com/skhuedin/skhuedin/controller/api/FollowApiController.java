package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.follow.FollowDeleteRequestDto;
import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.dto.follow.FollowSaveRequestDto;
import com.skhuedin.skhuedin.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("follows")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody FollowSaveRequestDto requestDto) {
        Long saveId = followService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(followService.findById(saveId)));
    }

    @GetMapping("follows/{followId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("followId") Long id) {
        FollowMainResponseDto responseDto = followService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @GetMapping("follows")
    public ResponseEntity<? extends BasicResponse> findAll() {
        List<FollowMainResponseDto> follows = followService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(follows));
    }

    @PutMapping("follows/{followId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("followId") Long id,
                                                          @Valid @RequestBody FollowSaveRequestDto updateDto) {
        Long blogId = followService.update(id, updateDto);
        FollowMainResponseDto responseDto = followService.findById(blogId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @DeleteMapping("follows")
    public ResponseEntity<? extends BasicResponse> delete(@RequestBody FollowDeleteRequestDto requestDto) {
        followService.delete(requestDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("follows/to-user/{toUserId}")
    public ResponseEntity<? extends BasicResponse> findByToUserId(@PathVariable("toUserId") Long toUserId) {
        List<FollowMainResponseDto> follows = followService.findByToUserId(toUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(follows));
    }

    @GetMapping("follows/from-user/{fromUserId}")
    public ResponseEntity<? extends BasicResponse> findByFromUserId(@PathVariable("fromUserId") Long fromUserId) {
        List<FollowMainResponseDto> follows = followService.findByFromUserId(fromUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(follows));
    }
}