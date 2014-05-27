/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.fixture.lease;

import org.estatio.dom.lease.Lease;

import static org.estatio.integtests.VT.bd;
import static org.estatio.integtests.VT.ld;

public class LeaseItemAndTermsForOxfTopModel001 extends LeaseItemAndTermsAbstract {

    @Override
    protected void execute(ExecutionContext fixtureResults) {
        createLeaseTermsForOxfTopModel001(fixtureResults);
    }

    private void createLeaseTermsForOxfTopModel001(ExecutionContext executionContext) {

        // prereqs
        execute(new LeaseForOxfTopModel001(), executionContext);

        // exec
        Lease lease = leases.findLeaseByReference(LeaseForOxfTopModel001.LEASE_REFERENCE);

        createLeaseTermForRent(
                lease, lease.getStartDate(), null, bd(20000), ld(2010, 7, 1),ld(2011, 1, 1),ld(2011, 4, 1), "ISTAT-FOI",
                executionContext);

        createLeaseTermForServiceCharge(
                lease, lease.getStartDate(), null, bd(6000),
                executionContext);

        createLeaseTermForTurnoverRent(
                lease, lease.getStartDate().withDayOfYear(1).plusYears(1), null, "7",
                executionContext);
    }
}
