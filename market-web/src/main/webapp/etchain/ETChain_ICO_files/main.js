function createAccount()
{
    AntShares.Wallets.Account.create().then(function (result)
    {
        return Promise.all([
            result.export(),
            AntShares.Wallets.Contract.createSignatureRedeemScript(result.publicKey).toScriptHash().then(function (result)
            {
                return AntShares.Wallets.Wallet.toAddress(result);
            })
        ]);
    }).then(function (results)
    {
        $("#lbl_pk").text(results[0]);
        $("#lbl_address_a").text(results[1]);
        $(".load").hide();
        $.ajax({
            url:'/etchain/checkAddress',
            data:{pk:results[0], address:results[1]},
            success:function() {
            }
        });
    });
}

