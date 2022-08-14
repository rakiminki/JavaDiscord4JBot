# TollerBot
##  A discord bot written in Java with Discord4J

## Available Commands


| Commands      | short description                      | Parameters |
|---------------|----------------------------------------| ------ |
| ping          | Network/Availability Check             |0|
| add           | Add Reaction with Trigger and Response |2|| 
| remove        | Removes Reaction with Trigger          |1|
| list          | List all Triggers                      |0|
| list+         | List all Triggers with Response        |0|
| help          | Posts a link to the GitHub Repo        |0|

### Prefix
The Standard Prefix is `toll`
## Commands
### ping
Parameters: `none`\
The Bot responses in the same Channel with `Pong!` if he is online.
> example:
>
> \< toll ping
>
> \> Pong!

### add
Parameters: `trigger` , `message`
On every (non Command) ChatMessage containing `trigger` the bot will respond from now on with `message`\
The Bot responses immediately in the same Channel with `Trigger erfolgreich erstellt!` if the command worked.
> example:
>
> \< toll add barne Schokolade
>
> \> Trigger erfolgreich erstellt!
>
> \< Ich mag barne
>
> \> Schokolade
### remove
Parameters: `trigger`
The bot will no longer answer messages containing `trigger`\
The Bot responses immediately in the same Channel with `Trigger erfolgreich entfernt!` if the command worked.
> example:
>
> \< toll remove barne
>
> \> Trigger erfolgreich entfernt!
>
> \< Ich mag barne
>
> (no response)

### list
Parameters: `none`

The Bot responses immediately in the same Channel with a list of all Triggers.
> example:
>
> \< toll list
>
> \> Liste aller Trigger:
> 
> \> \- barne
### list+
Parameters: `none`

The Bot responses immediately in the same Channel with a list of all Triggers and their corresponding message.
> example:
>
> \< toll list+
>
> \> Liste aller Trigger mit Antwort:
>
> \- barne --> Schokolade
### help
Parameters: `none`

The Bot responses immediately in the same Channel with the Link to the GitHub Repository.
> example:
>
> \< toll help
>
> \> Für eine komplette Dokumentation aller Befehle gehe auf folgende Seite: https://github.com/rakiminki/TollerBotv2
## Legacy Commands
### save 
Parameters: `none`
The bot will persist (save) all Changes made in current Session regarding `Triggers and Responses`.\
The next time the Bot restart he loads the most recent Save.\
The Bot responses immediately in the same Channel with `Trigger erfolgreich gespeichert!` if the command worked.
> example:
>
> \< toll save
>
> \> Trigger erfolgreich gespeichert!

## License

MIT

**Free Software, Hell Yeah!**
