package toyproject.bookbookclub.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Timeline.Timeline;

import java.sql.Time;

@Component
public class TimelineValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Timeline.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Timeline timeline = (Timeline) target;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bookId", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "required");
    }

}
