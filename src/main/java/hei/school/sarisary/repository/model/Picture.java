package hei.school.sarisary.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
  @Id private String id;
  private String originalBucketKey;
  private String blackBucketKey;

  @CreationTimestamp
  @Column(updatable = false)
  private Instant creationDatetime;

  @UpdateTimestamp private Instant updateDatetime;
}