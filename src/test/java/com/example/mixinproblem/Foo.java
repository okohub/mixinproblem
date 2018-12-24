package com.example.mixinproblem;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author onurozcan
 */
public class Foo implements Serializable {

  private String name;

  private String label;

  public String getName() {
    return name;
  }

  public String getLabel() {
    return label;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Foo)) {
      return false;
    }
    Foo foo = (Foo) o;
    return Objects.equals(getName(), foo.getName()) &&
           Objects.equals(getLabel(), foo.getLabel());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getLabel());
  }

  @Override
  public String toString() {
    return "Foo{" +
           "name='" + name + '\'' +
           ", label='" + label + '\'' +
           '}';
  }
}