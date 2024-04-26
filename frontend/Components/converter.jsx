import React from 'react';

const Hero = () => {
    // Replace these with actual data
    const country_iso_options = {};
    const converted_amount = null;
    const error = null;

    return (
        <div className="relative overflow-hidden before:absolute before:top-0 before:start-1/2 before:bg-[url('https://preline.co/assets/svg/examples/squared-bg-element.svg')] before:bg-no-repeat before:bg-top before:w-full before:h-full before:-z-[1] before:transform before:-translate-x-1/2">
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
                        <form method="POST" action="/" enctype="multipart/form-data" id="userForm">
                            <div className="z-40 mx-auto max-w-3xl p-3 bg-white border rounded-lg shadow-lg shadow-gray-100">
                                <div className="mx-auto max-w-3xl sm:flex sm:space-x-3 p-3 bg-white">
                                    <div className="pb-2 sm:pb-0 sm:flex-[1_0_0%]">
                                        <label htmlFor="countriesISO1" className="block mb-2 text-sm font-medium text-gray-900">County From: </label>
                                        <select required id="countriesISO1" name="countriesISO1"
                                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 ">
                                            <option selected disabled> Select an Option</option>
                                            {Object.entries(country_iso_options).map(([code, name]) => (
                                                <option required value={code}>{name}</option>
                                            ))}
                                        </select>
                                    </div>
                                    <br />
                                    <br />
                                    <div className="pt-2 sm:pt-0 sm:ps-3 border-t border-gray-200 sm:border-t-0 sm:border-s sm:flex-[1_0_0%] ">
                                        <label htmlFor="countriesISO2" className="block mb-2 text-sm font-medium text-gray-900">Country To:</label>
                                        <select required id="countriesISO2" name="countriesISO2"
                                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 ">
                                            <option>Select an Option</option>
                                            {Object.entries(country_iso_options).map(([code, name]) => (
                                                <option value={code}>{name}</option>
                                            ))}
                                        </select>
                                    </div>
                                    <div className="pt-2 sm:pt-0 sm:ps-3 border-t border-gray-200 sm:border-t-0 sm:border-s sm:flex-[1_0_0%] ">
                                        <label htmlFor="amount" className="block mb-2 text-sm font-medium text-gray-900">Amount:</label>
                                        <input required id="amount" name="amount" type="number" min="0" step="0.01"
                                               className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                               placeholder="Enter Amount" />
                                    </div>
                                </div>
                                <br />
                                <div className="mx-auto max-w-3xl sm:flex sm:space-x-3 p-3 bg-white">
                                    <button type="submit"
                                            className=" w-full py-3 inline-flex justify-center items-center gap-x-2 text-sm font-semibold rounded-lg border border-transparent bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50 disabled:pointer-events-none">
                                        Convert
                                    </button>
                                    {converted_amount !== null &&
                                        <button
                                            className=" w-full py-3 inline-flex justify-center items-center gap-x-2 text-sm font-semibold rounded-lg border border-transparent bg-orange-700 text-white"
                                            type="reset" value="Reset" onClick={() => window.location.reload()}>
                                            Reset
                                        </button>
                                    }
                                </div>
                                {converted_amount !== null && typeof converted_amount === 'number' &&
                                    <div className=" mx-[100px] bg-teal-100 border border-teal-200 text-lg text-center text-teal-800 rounded-lg p-4"
                                         role="alert">
                                        <span className="font-bold">Converted Ammount : </span> {converted_amount}
                                    </div>
                                }
                                {error &&
                                    <div className=" mx-[100px] bg-red-100 border border-red-200 text-sm text-center text-red-800 rounded-lg p-4"
                                         role="alert">
                                        <span className="font-bold">Danger</span> alert! You should check in on
                                        some of those fields
                                        below.
                                    </div>
                                }
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Hero;