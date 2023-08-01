package typo

package object dsl {
  type Joined[Fields1[_], Fields2[_], T] = (Fields1[T], Fields2[T])

  type LeftJoined[Fields1[_], Fields2[_], T] = (Fields1[T], OuterJoined[Fields2, T])

  type Required[T] = T

}
