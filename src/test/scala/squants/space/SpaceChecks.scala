/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.space

import org.scalacheck.Properties
import squants.QuantityChecks
import org.scalacheck.Prop._
import squants.motion.CubicMetersPerSecond
import squants.time.Seconds

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
object SpaceChecks extends Properties("Space") with QuantityChecks {

  implicit val tolVfr = CubicMetersPerSecond(tol)

  property("Area = Length * Length") = forAll(posNum, posNum) { (length1: TestData, length2: TestData) ⇒
    SquareMeters(length1 * length2) == Meters(length1) * Meters(length2) &&
      Meters(length1) == SquareMeters(length1 * length2) / Meters(length2)
  }

  property("Volume = Length * Area") = forAll(posNum, posNum) { (length: TestData, area: TestData) ⇒
    CubicMeters(length * area) == Meters(length) * SquareMeters(area) &&
      CubicMeters(length * area) == SquareMeters(area) * Meters(length) &&
      Meters(length) == CubicMeters(length * area) / SquareMeters(area) &&
      SquareMeters(area) == CubicMeters(length * area) / Meters(length)
  }

  property("Volume = VolumeFlowRate * Time") = forAll(posNum, posNum) { (vfr: TestData, time: TestData) ⇒
    CubicMeters(vfr * time) == CubicMetersPerSecond(vfr) * Seconds(time) &&
      CubicMeters(vfr * time) == Seconds(time) * CubicMetersPerSecond(vfr) &&
      Seconds(time) =~ CubicMeters(vfr * time) / CubicMetersPerSecond(vfr) &&
      CubicMetersPerSecond(vfr) =~ CubicMeters(vfr * time) / Seconds(time)
  }
}
