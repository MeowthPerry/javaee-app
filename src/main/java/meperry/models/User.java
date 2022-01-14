package meperry.models;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @RequiredArgsConstructor
public class User {
	private Integer id = -1;
	private Integer avatarId = 1;
	@NonNull private String name;
	@NonNull private String surname;
	@NonNull private String phone;
	@NonNull private String email;
	@NonNull private String password;
	private long commonMemory = 10485760;
}
