/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ SecondTimeDerivative, TimeSquared, TimeDerivative }

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value Double
 */
final class Yank private (val value: Double)
    extends Quantity[Yank]
    with TimeDerivative[Force]
    with SecondTimeDerivative[Momentum] {

  def valueUnit = Yank.valueUnit
  protected def timeIntegrated = Newtons(toNewtonsPerSecond)
  protected[squants] def time = Seconds(1)

  def *(that: TimeSquared): Momentum = this * that.time1 * that.time2

  def toNewtonsPerSecond = to(NewtonsPerSecond)
}

object Yank extends QuantityCompanion[Yank] {
  private[motion] def apply[A](n: A)(implicit num: Numeric[A]) = new Yank(num.toDouble(n))
  def apply = parseString _
  def name = "Yank"
  def valueUnit = NewtonsPerSecond
  def units = Set(NewtonsPerSecond)
}

trait YankUnit extends UnitOfMeasure[Yank] with UnitConverter {
  def apply[A](n: A)(implicit num: Numeric[A]) = Yank(convertFrom(n))
}

object NewtonsPerSecond extends YankUnit with ValueUnit {
  val symbol = "N/s"
}

object YankConversions {
  lazy val newtonPerSecond = NewtonsPerSecond(1)

  implicit class YankConversions[A](n: A)(implicit num: Numeric[A]) {
    def newtonsPerSecond = NewtonsPerSecond(n)
  }

  implicit object YankNumeric extends AbstractQuantityNumeric[Yank](Yank.valueUnit)
}
