status=published
type=post
title=ivy remote configs
date=2017-06-20
~~~~~~
I found an issue in an ivy.xml that resulted in hamcrest not being added to the test configuration. The junit
dependency looked like this:

    <dependency org="junit" name="junit" rev="4.12" conf="test->master"/>

The problem is `test->master` means only get the main artifacts from the remote dependency (no transitives). The
fix is to change `test->master` to `test->default`. `master` and `default` are the only two values available for a
maven repository.
