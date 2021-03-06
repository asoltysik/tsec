package tsec.mac

import cats.Functor
import tsec.common.VerificationStatus

trait MacAPI[A, MK[_]] {

  def sign[F[_]](in: Array[Byte], key: MK[A])(implicit M: MessageAuth[F, A, MK]): F[MAC[A]] =
    M.sign(in, key)

  def verifyBool[F[_]](in: Array[Byte], hashed: MAC[A], key: MK[A])(implicit M: MessageAuth[F, A, MK]): F[Boolean] =
    M.verifyBool(in, hashed, key)

  def verify[F[_]: Functor](in: Array[Byte], hashed: MAC[A], key: MK[A])(
      implicit M: MessageAuth[F, A, MK]
  ): F[VerificationStatus] =
    M.verify(in, hashed, key)

  @deprecated("Use verify and lift into the newtype", "0.0.1-M11")
  def verifyArrays[F[_]](in: Array[Byte], hashed: Array[Byte], key: MK[A])(
      implicit M: MessageAuth[F, A, MK]
  ): F[Boolean] =
    M.verifyBool(in, MAC[A](hashed), key)

}
