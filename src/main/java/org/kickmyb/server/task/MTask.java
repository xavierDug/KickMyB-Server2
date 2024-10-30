package org.kickmyb.server.task;

import org.kickmyb.server.photo.MPhoto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by joris on 15-09-15.
 */
@Entity
public class MTask {

    @Id     @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public Date creationDate;
    public Date deadline;
    public Boolean isDeleted;

    @Convert(converter = AttributeEncryptor.class)  // TODO exemple stupide, servirait plutôt pour NAS ou numero carte crédit
    public String name;

    @OneToMany(fetch=FetchType.EAGER)
    public List<MProgressEvent> events = new ArrayList<>();

    @OneToOne
    public MPhoto photo;

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != MTask.class) {
            return false;
        }
        return Objects.equals(((MTask) obj).id, id);
    }
}
