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
package org.estatio.module.application.demos;

import com.google.common.collect.Lists;

import org.apache.isis.applib.clock.TickingFixtureClock;
import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.seed.scripts.AbstractUserAndRolesFixtureScript;

import org.estatio.module.asset.fixtures.person.enums.Person_enum;
import org.estatio.module.asset.fixtures.property.enums.PropertyAndUnitsAndOwnerAndManager_enum;
import org.estatio.module.assetfinancial.fixtures.bankaccountfafa.enums.BankAccountFaFa_enum;
import org.estatio.module.assetfinancial.fixtures.bankaccountfafa.enums.BankAccount_enum;
import org.estatio.module.budget.fixtures.budgets.enums.Budget_enum;
import org.estatio.module.budget.fixtures.keytables.enums.KeyTable_enum;
import org.estatio.module.budget.fixtures.partitioning.enums.Partitioning_enum;
import org.estatio.module.capex.fixtures.document.enums.IncomingPdf_enum;
import org.estatio.module.capex.fixtures.incominginvoice.IncomingInvoiceFixture;
import org.estatio.module.capex.fixtures.order.OrderFixture;
import org.estatio.module.capex.fixtures.orderinvoice.OrderInvoiceFixture;
import org.estatio.module.capex.fixtures.project.enums.Project_enum;
import org.estatio.module.capex.seed.DocumentTypesAndTemplatesForCapexFixture;
import org.estatio.module.charge.EstatioChargeModule;
import org.estatio.module.charge.fixtures.incoming.builders.IncomingChargeFixture;
import org.estatio.module.country.IncodeDomCountryModule;
import org.estatio.module.currency.EstatioCurrencyModule;
import org.estatio.module.guarantee.fixtures.personas.GuaranteeForOxfTopModel001Gb;
import org.estatio.module.index.EstatioIndexModule;
import org.estatio.module.lease.fixtures.DocFragmentDemoFixture;
import org.estatio.module.lease.fixtures.bankaccount.personas.BankAccountAndMandateForPoisonNl;
import org.estatio.module.lease.fixtures.bankaccount.personas.BankAccountAndMandateForTopModelGb;
import org.estatio.module.lease.fixtures.breakoptions.personas.LeaseBreakOptionsForOxfMediax002Gb;
import org.estatio.module.lease.fixtures.breakoptions.personas.LeaseBreakOptionsForOxfPoison003Gb;
import org.estatio.module.lease.fixtures.breakoptions.personas.LeaseBreakOptionsForOxfTopModel001;
import org.estatio.module.lease.fixtures.invoicing.personas.InvoiceForLeaseItemTypeOfDiscountOneQuarterForOxfMiracle005;
import org.estatio.module.lease.fixtures.invoicing.personas.InvoiceForLeaseItemTypeOfRentOneQuarterForKalPoison001;
import org.estatio.module.lease.fixtures.invoicing.personas.InvoiceForLeaseItemTypeOfRentOneQuarterForOxfPoison003;
import org.estatio.module.lease.fixtures.lease.enums.Lease_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForDeposit_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForDiscount_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForPercentage_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForRent_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForServiceCharge_enum;
import org.estatio.module.lease.fixtures.leaseitems.enums.LeaseItemForTurnoverRent_enum;
import org.estatio.module.lease.migrations.CreateInvoiceNumerators;
import org.estatio.module.lease.seed.DocFragmentSeedFixture;
import org.estatio.module.lease.seed.DocumentTypesAndTemplatesForLeaseFixture;
import org.estatio.module.party.fixtures.numerator.personas.NumeratorForOrganisationFra;
import org.estatio.module.tax.EstatioTaxModule;

import static org.estatio.module.base.fixtures.security.apptenancy.enums.ApplicationTenancy_enum.Global;

public class EstatioDemoFixture extends DiscoverableFixtureScript {

    public EstatioDemoFixture() {
        this(null, "demo");
    }

    public EstatioDemoFixture(final String friendlyName, final String name) {
        super(friendlyName, name);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        TickingFixtureClock.replaceExisting();
        doExecute(executionContext);
    }

    private void doExecute(final ExecutionContext ec) {

        final AbstractUserAndRolesFixtureScript initialisationUser =
                new AbstractUserAndRolesFixtureScript(
                        "initialisation", "pass", null,
                        Global.getPath(), AccountType.LOCAL,
                        Lists.newArrayList("estatio-admin")) {
                };
        ec.executeChild(this, "'initialisation' user", initialisationUser);
        ec.executeChild(this, "countries", new IncodeDomCountryModule().getRefDataSetupFixture());
        ec.executeChild(this, "currencies", new EstatioCurrencyModule().getRefDataSetupFixture());
        ec.executeChild(this, "taxes", new EstatioTaxModule().getRefDataSetupFixture());
        ec.executeChild(this, "incomingCharges", new EstatioChargeModule().getRefDataSetupFixture());
        ec.executeChild(this, "indices", new EstatioIndexModule().getRefDataSetupFixture());

        ec.executeChildren(this, new DocFragmentDemoFixture(), new DocFragmentSeedFixture());

        ec.executeChildren(this,
                Person_enum.LinusTorvaldsNl,
                Person_enum.GinoVannelliGb, Person_enum.DylanOfficeAdministratorGb,
                Person_enum.JonathanPropertyManagerGb,
                Person_enum.FaithConwayGb,
                Person_enum.OscarCountryDirectorGb,
                Person_enum.EmmaTreasurerGb,
                Person_enum.ThibaultOfficerAdministratorFr,
                Person_enum.FifineLacroixFr,
                Person_enum.OlivePropertyManagerFr,
                Person_enum.RosaireEvrardFr,
                Person_enum.GabrielHerveFr,
                Person_enum.BrunoTreasurerFr);

        ec.executeChildren(this,
                PropertyAndUnitsAndOwnerAndManager_enum.GraIt,
                PropertyAndUnitsAndOwnerAndManager_enum.VivFr,
                PropertyAndUnitsAndOwnerAndManager_enum.HanSe,
                PropertyAndUnitsAndOwnerAndManager_enum.MnsFr,
                PropertyAndUnitsAndOwnerAndManager_enum.MacFr,
                PropertyAndUnitsAndOwnerAndManager_enum.CARTEST);

        ec.executeChild(this, new NumeratorForOrganisationFra());

        ec.executeChildren(this,
                BankAccount_enum.AcmeNl,
                BankAccount_enum.HelloWorldGb,
                BankAccount_enum.TopModelGb,
                BankAccount_enum.MediaXGb,
                BankAccount_enum.PretGb,
                BankAccount_enum.MiracleGb,
                BankAccount_enum.HelloWorldNl,
                BankAccountFaFa_enum.AcmeNl);

        ec.executeChildren(this,
                BankAccountFaFa_enum.HelloWorldNl,
                BankAccountFaFa_enum.HelloWorldGb
        );

        ec.executeChildren(this,
                new BankAccountAndMandateForTopModelGb(),
                new BankAccountAndMandateForPoisonNl());

        ec.executeChild(this, new GuaranteeForOxfTopModel001Gb());

        ec.executeChildren(this,
                Lease_enum.OxfMediaX002Gb,
                Lease_enum.OxfPret004Gb, Lease_enum.OxfMiracl005Gb,
                Lease_enum.KalPoison001Nl,
                Lease_enum.OxfTopModel001Gb);

        ec.executeChildren(this,
                new LeaseBreakOptionsForOxfTopModel001(),
                new LeaseBreakOptionsForOxfPoison003Gb(),
                new LeaseBreakOptionsForOxfMediax002Gb());

        ec.executeChildren(this,
                LeaseItemForRent_enum.OxfMiracl005Gb,
                LeaseItemForServiceCharge_enum.OxfMiracl005Gb,
                LeaseItemForTurnoverRent_enum.OxfMiracl005Gb,
                LeaseItemForDiscount_enum.OxfMiracle005bGb,
                LeaseItemForPercentage_enum.OxfMiracl005Gb,
                LeaseItemForDeposit_enum.OxfMiracle005bGb,
                LeaseItemForRent_enum.KalPoison001Nl);

        ec.executeChildren(this,
                new InvoiceForLeaseItemTypeOfRentOneQuarterForOxfPoison003(),
                new InvoiceForLeaseItemTypeOfRentOneQuarterForKalPoison001(),
                new InvoiceForLeaseItemTypeOfDiscountOneQuarterForOxfMiracle005());

        ec.executeChildren(this,
                Project_enum.KalProject1,
                Project_enum.KalProject2,
                Project_enum.GraProject);

        ec.executeChildren(this,
                Budget_enum.OxfBudget2015,
                Budget_enum.OxfBudget2016);

        ec.executeChildren(this,
                KeyTable_enum.Oxf2015Area,
                KeyTable_enum.Oxf2015Count);

        ec.executeChild(this, Partitioning_enum.OxfPartitioning2015.builder());

        ec.executeChild(this, new CreateInvoiceNumerators());

        ec.executeChild(this, new IncomingChargeFixture());
        ec.executeChild(this, new OrderInvoiceFixture());

        ec.executeChildren(this,
                new DocumentTypesAndTemplatesForCapexFixture(),
                new DocumentTypesAndTemplatesForLeaseFixture());

        ec.executeChildren(this,
                IncomingPdf_enum.FakeOrder1.builder().setRunAs("estatio-user-fr"),
                IncomingPdf_enum.FakeInvoice1.builder().setRunAs("estatio-user-fr"));

        ec.executeChild(this, new OrderFixture());
        ec.executeChild(this, new IncomingInvoiceFixture());

    }

}