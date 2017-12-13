package com.iopipe;

import java.io.PrintStream;
import java.util.Objects;

/**
 * This class contains the configuration for IOPipe and specifies the settings
 * which are to be used when the server is contacted.
 *
 * This class is mutable.
 *
 * @since 2017/12/12
 */
public final class IOPipeConfiguration
{
	/** Debug output stream, is optional. */
	protected final PrintStream debug;
	
	/** Should the service be enabled? */
	protected final boolean enabled;
	
	/** The project token to gather statistics for. */
	protected final String token;
	
	/**
	 * Initializes the configuration from the specified builder.
	 *
	 * @param __builder The builder to initialize from.
	 * @throws IllegalArgumentException If the input parameters are not
	 * correct.
	 * @throws NullPointerException On null arguments.
	 * @since 2017/12/13
	 */
	IOPipeConfiguration(IOPipeConfigurationBuilder __builder)
		throws IllegalArgumentException, NullPointerException
	{
		if (__builder == null)
			throw new NullPointerException();
		
		PrintStream debug = __builder._debug;
		boolean enabled = __builder._enabled;
		String token = __builder._token;
		
		if (token == null)
			throw new IllegalArgumentException("A project token must be " +
				"specified.");
		
		this.debug = debug;
		this.enabled = enabled;
		this.token = token;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/12/12
	 */
	@Override
	public final boolean equals(Object __o)
	{
		if (this == __o)
			return true;
		
		if (!(__o instanceof IOPipeConfiguration))
			return false;
		
		throw new Error("TODO");
	}
	
	/**
	 * Returns the debug stream where debugging information is printed to.
	 *
	 * @return The stream used for debugging.
	 * @since 2017/12/13
	 */
	public final PrintStream getDebugStream()
	{
		return this.debug;
	}
	
	/**
	 * Returns the token for the project to write statistics for.
	 *
	 * @return The project's token.
	 * @since 2017/12/13
	 */
	public final String getProjectToken()
	{
		return this.token;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/12/12
	 */
	@Override
	public final int hashCode()
	{
		throw new Error("TODO");
	}
	
	/**
	 * Returns {@code true} if IOPipe logging is to be enabled, this allows
	 * the service to be disabled for testing.
	 *
	 * @return {@code true} if logging is enabled.
	 * @since 2017/12/13
	 */
	public final boolean isEnabled()
	{
		return this.enabled;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 2017/12/12
	 */
	@Override
	public final String toString()
	{
		throw new Error("TODO");
	}
	
	/**
	 * This returns a configuration which is initialized by values using the
	 * default means of obtaining them via system properties and then
	 * environment variables.
	 *
	 * @return The default configuration to use.
	 * @since 2017/12/13
	 */
	public static final IOPipeConfiguration byDefault()
	{
		IOPipeConfigurationBuilder rv = new IOPipeConfigurationBuilder();
		
		// Enabled if not specified is "true" by default
		rv.setEnabled(Boolean.valueOf(Objects.toString(
			System.getProperty("com.iopipe.enabled",
			System.getenv("IOPIPE_ENABLED")), "true")));
		
		if (Boolean.valueOf(System.getProperty("com.iopipe.debug",
			System.getenv("IOPIPE_DEBUG"))))
			rv.setDebugStream(System.err);
		
		rv.setProjectToken(System.getProperty("com.iopipe.token",
			System.getenv("IOPIPE_TOKEN")));
		
		return rv.build();
	}
}
