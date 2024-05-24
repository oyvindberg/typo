package typo

package object dsl {
  type Joined[Fields1, Fields2] = (Fields1, Fields2)

  type LeftJoined[Fields1, Fields2] = (Fields1, OuterJoined[Fields2])

  type Required[T] = T

}
