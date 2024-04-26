import React, { useEffect, useState } from 'react';
import CurrencyConverterForm from './CurrencyConverterForm';
import { CurrencyConverterService } from 'Frontend/generated/endpoints';

export default function CurrencyConverterView() {
    const [conversionResult, setConversionResult] = useState<string | undefined>();
    const [amount, setAmount] = useState<string | undefined>();
    const onConvert = (countriesISO1: string, countriesISO2: string, amount: string) => {
        if (countriesISO1 === countriesISO2) {
            setConversionResult("Both countries can't be the same");
            return;
        }
        if (parseFloat(amount) <= 0) {
            setConversionResult("Amount must be greater than 0");
            return;
        }

        (async () => {
            const serverResponse : number | undefined = await CurrencyConverterService.convertCurrencyFromTo(countriesISO1, countriesISO2, amount.toString());
            if (serverResponse) {
                setAmount(serverResponse.toString());
                setConversionResult(`Converted ${countriesISO1} ${amount}  is ${countriesISO2}  ${serverResponse} `);
            } else {
                setConversionResult('No response from server');
            }
        })();
    };

    return (
        <>
            <div
                className=" relative overflow-hidden before:absolute before:top-0 before:start-1/2 before:bg-[url('https://preline.co/assets/svg/examples/squared-bg-element.svg')] before:bg-no-repeat before:bg-top before:w-full before:h-full before:-z-[1] before:transform before:-translate-x-1/2">
                <div className="overflow-hidden">
                    <div className="max-w-[85rem] mx-auto px-4 sm:px-6 lg:px-8 py-20">
                        <div className="relative mx-auto max-w-4xl grid space-y-5 sm:space-y-10">
                            <div className="text-center">
                                <div className="text-xs font-semibold text-gray-500 tracking-wide uppercase mb-3 ">

                                </div>
                                <h1 className="text-3xl text-gray-800 font-bold sm:text-5xl lg:text-6xl lg:leading-tight ">
                                    Convert Currency <br /> <span className="text-blue-500">Online with ease</span>
                                </h1>
                            </div>
                            <CurrencyConverterForm onConvert={onConvert} />
                            {conversionResult &&
                            (conversionResult === "Both countries can't be the same" || conversionResult === "No response from server" || conversionResult === "Amount must be greater than 0") ?
                                <div
                                    className=" mx-[100px] bg-red-100 border border-red-200 text-lg text-center text-white-800 rounded-lg p-4"
                                    role="alert">

                                    <span className="font-bold text-red-600">{conversionResult}</span>


                                </div>
                                :
                            amount && <div className=" mx-[100px] bg-teal-100 border border-teal-200 text-lg text-center text-teal-800 rounded-lg p-4"
                                               role="alert">

                                    <span className="font-bold">Converted Amount : {amount} </span> <br/>  <span className="font-bold">{conversionResult}</span>

                                </div>
                            }
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}