package umc.ShowHoo.web.performer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performer.service.PerformerService;


@RestController
@RequiredArgsConstructor
public class PerformerController {

    private final PerformerService performerService;
    private final PerformerRepository performerRepository;


}
