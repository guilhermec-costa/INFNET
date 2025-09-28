using AgenciaTurismo.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AgenciaTurismo.Pages;

public class TesteLogsModel : PageModel
{
    public List<string> MensagensLogMemoria { get; set; }

    public void OnGet()
    {
        var logService = new LogService();
        Action<string> logAction = logService.LogToConsole;
        logAction += logService.LogToFile;
        logAction += logService.LogToMemory;

        logAction("Operação de teste realizada com sucesso.");

        MensagensLogMemoria = LogService.LogMessages;
    }
}