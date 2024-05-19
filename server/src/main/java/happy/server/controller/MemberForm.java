package happy.server.controller;

import happy.server.entity.Authority;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MemberForm {

    @NotNull(message = "전화번호를 입력해 주세요.")
    private Long id;
    private String name;
    private String password;
    private Authority authority;

}
