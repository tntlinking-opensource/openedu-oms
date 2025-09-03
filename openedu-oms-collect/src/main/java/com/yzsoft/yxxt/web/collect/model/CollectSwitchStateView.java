package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.StringIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;

/*
create or replace view yxw_collect_switch_state_views as
select s.id || '_' || cs.id as id,
       css.id state_id,
       s.id student_id,
       cs.id COLLECT_SWITCH_ID
  from yxw_collect_switches cs
 cross join cp_students s
  left join YXW_COLLECT_SWITCH_STATES css
    on s.id = css.student_id
   and cs.id = css.collect_switch_id
where cs.enabled = 1
 */
@Entity
public class CollectSwitchStateView extends StringIdObject {

    private Student student;
    private CollectSwitch collectSwitch;
    private CollectSwitchState state;

    public CollectSwitchState getState() {
        return state;
    }

    public void setState(CollectSwitchState state) {
        this.state = state;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CollectSwitch getCollectSwitch() {
        return collectSwitch;
    }

    public void setCollectSwitch(CollectSwitch collectSwitch) {
        this.collectSwitch = collectSwitch;
    }
}
