package com.iopipe;

import com.amazonaws.services.lambda.runtime.Context;
import com.iopipe.plugin.IOpipePluginExecution;
import com.iopipe.plugin.NoSuchPluginException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class provides access to information and functionality which is
 * specific to a single execution of a method.
 *
 * Each execution will have a unique instance of this object and as such will
 * be initialized when it is first used.
 *
 * The {@link com.amazonaws.services.lambda.runtime.Context} object can be
 * obtained by invoking the {@link #context()} method.
 *
 * @since 2018/01/19
 */
public abstract class IOpipeExecution
{
	/** Was this detected to be a coldstart? */
	protected final boolean coldstart;
	
	/**
	 * Initializes the base execution.
	 *
	 * @param __cold Is this a cold start?
	 * @since 2018/08/27
	 */
	IOpipeExecution(boolean __cold)
	{
		this.coldstart = __cold;
	}
	
	/**
	 * Adds a single performance entry to the report.
	 *
	 * @param __e The entry to add to the report.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/19
	 */
	public abstract void addPerformanceEntry(PerformanceEntry __e)
		throws NullPointerException;
	
	/**
	 * Returns the configuration used to initialize the service.
	 *
	 * @return The service configuration.
	 * @since 2018/01/19
	 */
	public abstract IOpipeConfiguration config();
	
	/**
	 * Returns the context for the Amazon Web Service Lambda execution that
	 * is currently running. If it is not known or is valid then a placeholder
	 * context will be returned.
	 *
	 * @return The AWS context.
	 * @since 2018/01/19
	 */
	public abstract Context context();
	
	/**
	 * Adds a single custom metric to the report.
	 *
	 * @param __cm The custom metric to add.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/20
	 */
	public abstract void customMetric(CustomMetric __cm)
		throws NullPointerException;
	
	/**
	 * Adds the specified custom metric with a string value.
	 *
	 * Custom metric names are limited to the length specified in
	 * {@link IOpipeConstants#NAME_CODEPOINT_LIMIT}.
	 *
	 * @param __name The metric name.
	 * @param __sv The string value.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/30
	 */
	public abstract void customMetric(String __name, String __sv)
		throws NullPointerException;
	
	/**
	 * Adds the specified custom metric with a long value.
	 *
	 * Custom metric names are limited to the length specified in
	 * {@link IOpipeConstants#NAME_CODEPOINT_LIMIT}.
	 *
	 * @param __name The metric name.
	 * @param __lv The long value.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/30
	 */
	public abstract void customMetric(String __name, long __lv)
		throws NullPointerException;
	
	/**
	 * Returns a copy of the custom metrics which were measured.
	 *
	 * @return The custom metrics which were measured.
	 * @since 2018/03/15
	 */
	public abstract CustomMetric[] getCustomMetrics();
	
	/**
	 * Returns all of the labels which have been declared during the
	 * execution.
	 *
	 * @return The labels which have been declared during execution.
	 * @since 2018/04/11
	 */
	public abstract String[] getLabels();
	
	/**
	 * Returns a copy of the performance entries which were measured.
	 *
	 * @return The performance entries which were measured.
	 * @since 2018/03/15
	 */
	public abstract PerformanceEntry[] getPerformanceEntries();
	
	/**
	 * Returns the object which was used as input for the method being
	 * executed, {@code null} will be returned if it was not passed or is not
	 * known.
	 *
	 * @return The extra object which was passed to the run method or
	 * {@code null} if it was not passed or is not known.
	 * @since 2018/04/16
	 */
	public abstract Object input();
	
	/**
	 * Adds a single label which will be passed in the report.
	 *
	 * Labels are limited to the length specified in
	 * {@link IOpipeConstants#NAME_CODEPOINT_LIMIT}.
	 *
	 * @param __s The label to add.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/04/11
	 */
	public abstract void label(String __s)
		throws NullPointerException;
	
	/**
	 * This returns an instance of a plugin based on the class type of its
	 * interface, if the plugin does not exist then {@code null} is returned.
	 *
	 * @param <C> The class type of the execution state.
	 * @param __cl The class object of the execution state.
	 * @return The instance of the plugin's execution state or {@code null}
	 * if no such plugin exists.
	 * @throws ClassCastException If the class type is not valid.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/23
	 */
	public abstract <C extends IOpipePluginExecution> C optionalPlugin(
		Class<C> __cl)
		throws ClassCastException, NullPointerException;
	
	/**
	 * Returns the service which ran this execution.
	 *
	 * @return The service which ran this execution.
	 * @since 2018/01/19
	 */
	public abstract IOpipeService service();
	
	/**
	 * Returns an instance of the signer which is used to obtain a URL for
	 * uploading.
	 *
	 * @param __ext The extension of the file to upload, if {@code null} then
	 * it is not specified.
	 * @return The signer for uploading or {@code null} if no signer is
	 * available for usage.
	 * @since 2018/09/24
	 */
	public abstract IOpipeSigner signer(String __ext);
	
	/**
	 * Returns the starting time of the execution on the wall clock.
	 *
	 * @return The starting time in milliseconds.
	 * @since 2018/02/16
	 */
	public abstract long startTimestamp();
	
	/**
	 * Adds multiple custom metrics in a single bulk operation.
	 *
	 * Parameters which are {@code null} are ignored.
	 *
	 * @param __cms The custom metrics to add.
	 * @since 2018/04/24
	 */
	public final void customMetrics(CustomMetric... __cms)
	{
		// Do nothing
		if (__cms == null)
			return;
			
		// Add all metrics
		for (CustomMetric cm : __cms)
			if (cm != null)
				this.customMetric(cm);
	}
	
	/**
	 * Returns the object which was used as input for the method being
	 * executed.
	 *
	 * @param <T> The type of object to return.
	 * @param __cl The type of object to return.
	 * @return The extra object which was passed to the run method.
	 * @throws ClassCastException If it is not of the passed class type.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/04/16
	 */
	public final <T> T input(Class<T> __cl)
		throws ClassCastException, NullPointerException
	{
		if (__cl == null)
			throw new NullPointerException();
		
		return __cl.cast(this.input());
	}
	
	/**
	 * Is this a coldstarted execution?
	 *
	 * @return If this is a coldstarted execution.
	 * @since 2018/03/15
	 */
	public final boolean isColdStarted()
	{
		return this.coldstart;
	}
	
	/**
	 * This returns an instance of a plugin based on the class type of its
	 * interface.
	 *
	 * @param <C> The class type of the execution state.
	 * @param __cl The class object of the execution state.
	 * @return The instance of the plugin's execution state.
	 * @throws ClassCastException If the class type is not valid.
	 * @throws NoSuchPluginException If the plugin does not exist.
	 * @throws NullPointerException On null arguments.
	 * @since 2018/01/20
	 */
	public final <C extends IOpipePluginExecution> C plugin(Class<C> __cl)
		throws ClassCastException, NoSuchPluginException, NullPointerException
	{
		C rv = this.optionalPlugin(__cl);
		if (rv == null)
			throw new NoSuchPluginException("No plugin exists, it is disabled, " +
				"or it failed to initialize for execution class " + __cl);
		return rv;
	}
	
	/**
	 * Returns the thread group which this execution is running under.
	 *
	 * @return The thread group of this execution, may return .
	 * @since 2018/02/09
	 */
	public final ThreadGroup threadGroup()
	{
		return Thread.currentThread().getThreadGroup();
	}
	
	/**
	 * Returns the current execution for the given thread.
	 *
	 * @return The execution context which is associated with this thread, if
	 * there is no valid execution context then one that does nothing will be
	 * created.
	 * @since 2018/07/30
	 */
	public static final IOpipeExecution currentExecution()
	{
		IOpipeExecution rv = IOpipeService.__execution();
		if (rv == null)
			return new __NoOpExecution__(!IOpipeService._THAWED.get());
		return rv;
	}
}

