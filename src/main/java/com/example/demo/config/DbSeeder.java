package com.example.demo.config;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.video.Video;
import com.example.demo.video.VideoRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DbSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public void run(String... args) throws Exception {
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();
        System.out.println(userId + " |||| " + videoId);

        User newUser = User.builder().email("gg@gmail.com").name("gg").password("gg").role(Role.USER).build();
        userRepository.save(newUser);
        Video vid = Video.builder().uploader(newUser).title("cool vid").path("./src/main/resources/storage/som.mp4")
                .build();
        videoRepository
                .save(vid);

    }

}
