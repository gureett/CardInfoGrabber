using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Dynamic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CardInfoGrabber
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void checkButton_Click(object sender, EventArgs e)
        {
            resultTextBox.Text = string.Empty;
            string cardNumber = cardNumberTextBox.Text.Trim();

            if (IsValidCardNumber(cardNumber))
            {
                CardInfo cardInfo = GetCardInfo(cardNumber);
                if (cardInfo != null)
                {
                    resultTextBox.Text = cardInfo.ToString();
                }
                else
                {
                    resultTextBox.Text = "Unable to retrieve card information";
                }
            }
            else
            {
                resultTextBox.Text = "Invalid card number";
            }
        }

        private bool IsValidCardNumber(string cardNumber)
        {
            string sanitizedCardNumber = new string(cardNumber.ToCharArray()
                .Where(c => char.IsDigit(c)).ToArray());

            if (sanitizedCardNumber.Length < 12 || sanitizedCardNumber.Length > 19)
            {
                return false;
            }

            int sum = 0;
            bool alternate = false;

            for (int i = sanitizedCardNumber.Length - 1; i >= 0; i--)
            {
                int digit = int.Parse(sanitizedCardNumber[i].ToString());

                if (alternate)
                {
                    digit *= 2;
                    if (digit > 9)
                    {
                        digit = (digit % 10) + 1;
                    }
                }

                sum += digit;
                alternate = !alternate;
            }

            return sum % 10 == 0;
        }

        private CardInfo GetCardInfo(string cardNumber)
        {
            string bin = cardNumber.Substring(0, 6);
            string apiUrl = "https://lookup.binlist.net/" + bin;

            try
            {
                WebRequest request = WebRequest.Create(apiUrl);
                request.Method = "GET";

                using (WebResponse response = request.GetResponse())
                using (Stream responseStream = response.GetResponseStream())
                using (StreamReader reader = new StreamReader(responseStream, Encoding.UTF8))
                {
                    string jsonResponse = reader.ReadToEnd();
                    JObject jsonObject = JObject.Parse(jsonResponse);

                    CardInfo cardInfo = new CardInfo();
                    cardInfo.CardType = jsonObject["type"].ToString();
                    cardInfo.Bank = jsonObject["bank"]["name"].ToString();
                    cardInfo.Country = jsonObject["country"]["name"].ToString();
                    cardInfo.Scheme = jsonObject["scheme"].ToString();
                    cardInfo.Brand = jsonObject["brand"].ToString();

                    return cardInfo;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        private class CardInfo
        {
            public string CardType { get; set; }
            public string Bank { get; set; }
            public string Country { get; set; }
            public string Scheme { get; set; }
            public string Brand { get; set; }

            public override string ToString()
            {
                return $"Card Type: {CardType}\n" +
                       $"Bank: {Bank}\n" +
                       $"Country: {Country}\n" +
                       $"Scheme: {Scheme}\n" +
                       $"Brand: {Brand}";
            }
        }
    }
}
