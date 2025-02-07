package org.ject.support.external.dynamodb.domain;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompositeKey implements Comparable<CompositeKey>, Serializable {
    public static final String DELIMITER = "#";

    private String prefix;
    private String postfix;

    @Override
    public String toString() {
        return prefix + DELIMITER + postfix;
    }

    @Override
    public int compareTo(CompositeKey compositeKey) {
        return this.toString().compareTo(compositeKey.toString());
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompositeKey that)) {
            return false;
        }

        return Objects.equals(prefix, that.prefix) && Objects.equals(postfix, that.postfix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(prefix);
        result = 31 * result + Objects.hashCode(postfix);
        return result;
    }
}
