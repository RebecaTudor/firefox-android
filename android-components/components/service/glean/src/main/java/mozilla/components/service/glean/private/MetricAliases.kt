/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.service.glean.private

import androidx.annotation.VisibleForTesting

typealias CommonMetricData = mozilla.telemetry.glean.private.CommonMetricData
typealias EventExtras = mozilla.telemetry.glean.private.EventExtras
typealias Lifetime = mozilla.telemetry.glean.private.Lifetime
typealias NoExtras = mozilla.telemetry.glean.private.NoExtras
typealias NoReasonCodes = mozilla.telemetry.glean.private.NoReasonCodes
typealias ReasonCode = mozilla.telemetry.glean.private.ReasonCode

typealias BooleanMetricType = mozilla.telemetry.glean.private.BooleanMetricType
typealias CounterMetricType = mozilla.telemetry.glean.private.CounterMetricType
typealias CustomDistributionMetricType = mozilla.telemetry.glean.private.CustomDistributionMetricType
typealias DatetimeMetricType = mozilla.telemetry.glean.private.DatetimeMetricType
typealias DenominatorMetricType = mozilla.telemetry.glean.private.DenominatorMetricType
typealias HistogramMetricBase = mozilla.telemetry.glean.private.HistogramBase
typealias HistogramType = mozilla.telemetry.glean.private.HistogramType
typealias LabeledMetricType<T> = mozilla.telemetry.glean.private.LabeledMetricType<T>
typealias MemoryDistributionMetricType = mozilla.telemetry.glean.private.MemoryDistributionMetricType
typealias MemoryUnit = mozilla.telemetry.glean.private.MemoryUnit
typealias NumeratorMetricType = mozilla.telemetry.glean.private.NumeratorMetricType
typealias PingType<T> = mozilla.telemetry.glean.private.PingType<T>
typealias QuantityMetricType = mozilla.telemetry.glean.private.QuantityMetricType
typealias RateMetricType = mozilla.telemetry.glean.private.RateMetricType
typealias RecordedExperiment = mozilla.telemetry.glean.private.RecordedExperiment
typealias StringListMetricType = mozilla.telemetry.glean.private.StringListMetricType
typealias StringMetricType = mozilla.telemetry.glean.private.StringMetricType
typealias TextMetricType = mozilla.telemetry.glean.private.TextMetricType
typealias TimeUnit = mozilla.telemetry.glean.private.TimeUnit
typealias TimespanMetricType = mozilla.telemetry.glean.private.TimespanMetricType
typealias TimingDistributionMetricType = mozilla.telemetry.glean.private.TimingDistributionMetricType
typealias UrlMetricType = mozilla.telemetry.glean.private.UrlMetricType
typealias UuidMetricType = mozilla.telemetry.glean.private.UuidMetricType

// FIXME(bug 1885170): Wrap the Glean SDK `EventMetricType` to overwrite the `testGetValue` function.
/**
 * This implements the developer facing API for recording events.
 *
 * Instances of this class type are automatically generated by the parsers at built time,
 * allowing developers to record events that were previously registered in the metrics.yaml file.
 *
 * The Events API only exposes the [record] method, which takes care of validating the input
 * data and making sure that limits are enforced.
 */
class EventMetricType<ExtraObject> internal constructor(
    private var inner: mozilla.telemetry.glean.private.EventMetricType<ExtraObject>,
) where ExtraObject : EventExtras {
    /**
     * The public constructor used by automatically generated metrics.
     */
    constructor(meta: CommonMetricData, allowedExtraKeys: List<String>) :
        this(inner = mozilla.telemetry.glean.private.EventMetricType(meta, allowedExtraKeys))

    /**
     * Record an event by using the information provided by the instance of this class.
     *
     * @param extra The event extra properties.
     *              Values are converted to strings automatically
     *              This is used for events where additional richer context is needed.
     *              The maximum length for values is 100 bytes.
     *
     * Note: `extra` is not optional here to avoid overlapping with the above definition of `record`.
     *       If no `extra` data is passed the above function will be invoked correctly.
     */
    fun record(extra: ExtraObject? = null) {
        inner.record(extra)
    }

    /**
     * Returns the stored value for testing purposes only. This function will attempt to await the
     * last task (if any) writing to the the metric's storage engine before returning a value.
     *
     * @param pingName represents the name of the ping to retrieve the metric for.
     *                 Defaults to the first value in `sendInPings`.
     * @return value of the stored events
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @JvmOverloads
    fun testGetValue(pingName: String? = null): List<mozilla.telemetry.glean.private.RecordedEvent>? {
        var events = inner.testGetValue(pingName)
        if (events == null) {
            return events
        }

        // Remove the `glean_timestamp` extra.
        // This is added by Glean and does not need to be exposed to testing.
        for (event in events) {
            if (event.extra == null) {
                continue
            }

            // We know it's not null
            var map = event.extra!!.toMutableMap()
            map.remove("glean_timestamp")
            if (map.isEmpty()) {
                event.extra = null
            } else {
                event.extra = map
            }
        }

        return events
    }

    /**
     * Returns the number of errors recorded for the given metric.
     *
     * @param errorType The type of the error recorded.
     * @return the number of errors recorded for the metric.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun testGetNumRecordedErrors(errorType: mozilla.components.service.glean.testing.ErrorType) =
        inner.testGetNumRecordedErrors(errorType)
}
