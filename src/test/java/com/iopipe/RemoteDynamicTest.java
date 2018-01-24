package com.iopipe;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * This initializes tests which uses and interacts with the real service.
 *
 * @since 2018/01/23
 */
@RunWith(JUnitPlatform.class)
public class RemoteDynamicTest
{
	/**
	 * Initializes all of the tests.
	 *
	 * @return All of the test.
	 * @since 2018/01/23
	 */
	@TestFactory
	public Collection<DynamicTest> doThings()
	{
		throw new Error("TODO");
	}
}

