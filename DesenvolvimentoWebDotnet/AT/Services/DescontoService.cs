namespace AgenciaTurismo.Services;

public delegate decimal CalculateDelegate(decimal precoOriginal);

public class DescontoService
{
    public decimal AplicarDescontoCliente(decimal precoOriginal)
    {
        return precoOriginal * 0.9m;
    }
}