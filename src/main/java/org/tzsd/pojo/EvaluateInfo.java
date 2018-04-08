package org.tzsd.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description: 评价
 */
@Entity
@Table(name = "evaluation_info")
public class EvaluateInfo {
    @EmbeddedId
    private EvaluateKey evaluateKey;

    @Column(name = "evaluate")
    @Enumerated(EnumType.STRING)
    private Evaluate evaluate;

    @Column(name = "descs")
    private String descs;

    @Column(name = "img_1")
    private String img1;

    @Column(name = "img_2")
    private String img2;

    @Column(name = "img_3")
    private String img3;

    enum Evaluate{
        YES,
        NO
    }

    /**
     * @description: EvaluateInfo的主键
     */
    @Embeddable
    static class EvaluateKey implements Serializable{
        private String goods_id;

        private long user_id;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EvaluateKey that = (EvaluateKey) o;

            if (user_id != that.user_id) return false;
            return goods_id != null ? goods_id.equals(that.goods_id) : that.goods_id == null;

        }

        @Override
        public int hashCode() {
            int result = goods_id != null ? goods_id.hashCode() : 0;
            result = 31 * result + (int) (user_id ^ (user_id >>> 32));
            return result;
        }
    }
}
