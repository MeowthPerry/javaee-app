package meperry.models;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @RequiredArgsConstructor
public class Login {
	private Integer id;
	@NonNull private Integer userId;
	@NonNull private String date;
	@NonNull private String time;
	@NonNull private String ip;
}
