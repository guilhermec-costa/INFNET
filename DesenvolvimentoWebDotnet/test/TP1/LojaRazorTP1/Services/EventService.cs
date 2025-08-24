using LojaRazor.Models;

namespace LojaRazor.Services
{
    public class EventService
    {
        public event Action<Event> OnEventCreated;

        public void AddEvent(Event novoEvento)
        {
            OnEventCreated?.Invoke(novoEvento);
        }
    }
}
