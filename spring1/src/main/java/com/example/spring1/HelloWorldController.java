package com.example.spring1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
public class HelloWorldController {
    private int count;

    private boolean validate(Long unixTime) {
        if (unixTime == 0) {
            throw new RuntimeException("Invalid unixTime: 0 is invalid");
        }
        if (unixTime < 0) {
            throw new RuntimeException("Invalid unixTime: can not be negative");
        }
        return true;
    }

    @GetMapping("/level1/{unixTime}")
    public ResponseEntity<String> convertTimeUnixToNormal(
            @PathVariable("unixTime") Long unixTime
    ) {
        try {
            validate(unixTime);
            Date date = new Date(unixTime * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
            String formattedDate = sdf.format(date);
            return ResponseEntity.ok(formattedDate);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //convert to normal Date object

    }

    @GetMapping("/level2/{unixTime}")
    public ResponseEntity<String> getYearsAndDays(
            @PathVariable("unixTime") Long unixTime
    ) {
        try {
            Instant instant = Instant.ofEpochSecond(unixTime);
            LocalDate date = LocalDate.ofInstant(instant, ZoneId.of("UTC"));
            return ResponseEntity.ok("Years: " + (LocalDate.now().getYear() - date.getYear()) + "\n"
                    + "Days: " + ChronoUnit.DAYS.between(date, LocalDate.now()));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/level3/{unixTimePath}")
    public ResponseEntity<String> getYearsAndDays2(
            @PathVariable(value = "unixTimePath", required = false) Long unixTimePath,
            @RequestParam(required = false) Long unixTimeParam,
            @RequestHeader(value = "unixHeader", required = false) Long unixHeader,
            @RequestBody(required = false) Long unixBody
    ) {
        try {
            validate(unixTimePath);
            Instant instant = Instant.ofEpochSecond(unixTimePath);

            if (unixTimeParam != null) {
                validate(unixTimeParam);
                instant = Instant.ofEpochSecond(unixTimeParam);
            }
            if (unixHeader != null) {
                validate(unixHeader);
                instant = Instant.ofEpochSecond(unixHeader);
            }
            if (unixBody != null) {
                validate(unixBody);
                instant = Instant.ofEpochSecond(unixBody);
            }
            LocalDate date = LocalDate.ofInstant(instant, ZoneId.of("UTC"));
            return ResponseEntity.ok("Years: " + (LocalDate.now().getYear() - date.getYear()) + "\n"
                    + "Days: " + ChronoUnit.DAYS.between(date, LocalDate.now()));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/level4")
    public ResponseEntity<String> convertToDate(@RequestParam("stringTime") String str) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(str, formatter);
            return ResponseEntity.ok("Years: " + (LocalDate.now().getYear() - date.getYear()) + "\n"
                    + " - Days: " + ChronoUnit.DAYS.between(date, LocalDate.now()));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Incorrect format String" + e.getMessage());
        }
    }

    @GetMapping("/level5")
    public ResponseEntity<String> convertToDate2(@RequestParam("stringTime") String str) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(str, formatter);
            count++;
            return ResponseEntity.ok("Years: " + (LocalDate.now().getYear() - date.getYear()) + "\n"
                    + " - Days: " + ChronoUnit.DAYS.between(date, LocalDate.now()) + "\n"
            + " - Count: " + count);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Incorrect format String" + e.getMessage());
        }

    }
}
