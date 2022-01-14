package meperry.models;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @RequiredArgsConstructor
public class Image {
	private Integer id;
	@NonNull private Integer userId;
	@NonNull private String originalName;
	@NonNull private String uniqueName;
	@NonNull private Long size;
	@NonNull private String mime;
}
