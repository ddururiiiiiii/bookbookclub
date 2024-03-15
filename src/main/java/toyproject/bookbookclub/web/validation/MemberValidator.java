package toyproject.bookbookclub.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Members.MemberJoinForm;

@Component
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinForm member = (MemberJoinForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
    }

}
