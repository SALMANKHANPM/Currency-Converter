import React, { useState, useEffect, ReactElement } from 'react';
import { ComboBox } from '@hilla/react-components/ComboBox.js';
import { CurrencyConverterService } from 'Frontend/generated/endpoints.js';

 function CurrencyConverterForm({ onConvert }: { onConvert: (countriesISO1: string, countriesISO2: string, amount: string) => void }): ReactElement {
    const [countriesISO1, setCountriesISO1] = useState('');
    const [countriesISO2, setCountriesISO2] = useState('');
    const [amount, setAmount] = useState('');
    const [isoOptions, setIsoOptions] = useState<{ value: string; label: string; }[]>([]);

    useEffect(() => {
        const fetchCountryIsoOptions = async () => {
            const countryIsoOptions = await CurrencyConverterService.getCountryIsoOptions();
            const formattedOptions: { value: string; label: string; }[] = Object.entries(countryIsoOptions).map(([value, label]) => ({ value, label: label as string }));
            setIsoOptions(formattedOptions);
        };

        fetchCountryIsoOptions().then(r =>  console.log(r)) === undefined;
    }, []);

    const handleSubmit = (event: any) => {
        event.preventDefault();
        onConvert(countriesISO1, countriesISO2, amount);
    };

    return (
        <form onSubmit={handleSubmit} id="userForm">
            <div className="z-40 mx-auto max-w-3xl p-3 bg-white border rounded-lg shadow-lg shadow-gray-100">
                <div className="mx-auto max-w-3xl sm:flex sm:space-x-3 p-3 bg-white">
                    <div className="pb-2 sm:pb-0 sm:flex-[1_0_0%]">
                        <label htmlFor="countriesISO1" className="block mb-2 text-sm font-medium text-gray-900">Country From: </label>
                        <ComboBox
                            allowCustomValue
                            helperText="Select or type a country"
                            items={isoOptions}
                            onValueChanged={(e) => setCountriesISO1(e.detail.value)}
                        />
                    </div>
                    <div className="pb-2 sm:pb-0 sm:flex-[1_0_0%]">
                        <label htmlFor="countriesISO2" className="block mb-2 text-sm font-medium text-gray-900">Country To: </label>
                        <ComboBox
                            allowCustomValue
                            helperText="Select or type a country"
                            items={isoOptions}
                            onValueChanged={(e) => setCountriesISO2(e.detail.value)}
                        />
                    </div>
                    <div className="pb-2 sm:pb-0 sm:flex-[1_0_0%]">
                        <label htmlFor="amount" className="block mb-2 text-sm font-medium text-gray-900">Amount: </label>
                        <input required id="amount" name="amount" type="number" min='0' step='.01'
                               className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                               placeholder="Enter Amount" value={amount} onChange={(e) => setAmount(e.target.value)} />
                    </div>
                </div>
                <div className="mx-auto max-w-3xl sm:flex sm:space-x-3 p-3 bg-white">
                    <button type="submit"
                            className="w-full py-3 inline-flex justify-center items-center gap-x-2 text-sm font-semibold rounded-lg border border-transparent bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50 disabled:pointer-events-none">
                        Convert
                    </button>
                </div>
            </div>
        </form>
    );
}

export default CurrencyConverterForm;