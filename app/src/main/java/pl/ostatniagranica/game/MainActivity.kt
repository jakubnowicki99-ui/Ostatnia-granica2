package pl.ostatniagranica.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OstatniaGranicaApp() }
    }
}

@Composable
fun OstatniaGranicaApp() {
    var wood by remember { mutableIntStateOf(180) }
    var stone by remember { mutableIntStateOf(120) }
    var food by remember { mutableIntStateOf(95) }
    var steel by remember { mutableIntStateOf(30) }
    var people by remember { mutableIntStateOf(24) }
    var soldiers by remember { mutableIntStateOf(8) }
    var territory by remember { mutableIntStateOf(1) }
    var log by remember { mutableStateOf(listOf("Obóz ocalałych założony.", "Zamek jest gotowy. Czekamy na pierwszych zwiadowców.")) }

    fun event(text: String) { log = listOf(text) + log.take(7) }

    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface(Modifier.fillMaxSize(), color = Color(0xFF101214)) {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val wide = maxWidth >= 700.dp
                Column(Modifier.fillMaxSize()) {
                    ResourceBar(wood, stone, food, steel, people, soldiers, territory)
                    if (wide) {
                        Row(Modifier.fillMaxSize()) {
                            CityView(Modifier.weight(1f).fillMaxHeight(), territory)
                            ControlPanel(
                                Modifier.width(330.dp).fillMaxHeight(),
                                onWood = { wood += 25; event("Drwale dostarczyli 25 drewna.") },
                                onSteel = { if (wood >= 20) { wood -= 20; steel += 10; event("Wytopiono 10 stali.") } },
                                onTower = {
                                    if (wood >= 80 && stone >= 50) { wood -= 80; stone -= 50; territory++; event("Wzniesiono wieżę strażniczą. Terytorium powiększone.") }
                                    else event("Brakuje 80 drewna i 50 kamienia.")
                                },
                                onScout = { event("Zwiadowca wrócił: na zachodzie znaleziono duży las i złoże żelaza.") },
                                onRecruit = { if (food >= 10 && people > soldiers) { food -= 10; soldiers++; event("Nowy rekrut dołączył do oddziału Alfa.") } },
                                log = log
                            )
                        }
                    } else {
                        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                            CityView(Modifier.fillMaxWidth().height(360.dp), territory)
                            ControlPanel(
                                Modifier.fillMaxWidth(),
                                onWood = { wood += 25; event("Drwale dostarczyli 25 drewna.") },
                                onSteel = { if (wood >= 20) { wood -= 20; steel += 10; event("Wytopiono 10 stali.") } },
                                onTower = { if (wood >= 80 && stone >= 50) { wood -= 80; stone -= 50; territory++; event("Wzniesiono wieżę strażniczą. Terytorium powiększone.") } else event("Brakuje 80 drewna i 50 kamienia.") },
                                onScout = { event("Zwiadowca wrócił: na zachodzie znaleziono duży las i złoże żelaza.") },
                                onRecruit = { if (food >= 10 && people > soldiers) { food -= 10; soldiers++; event("Nowy rekrut dołączył do oddziału Alfa.") } },
                                log = log
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResourceBar(wood: Int, stone: Int, food: Int, steel: Int, people: Int, soldiers: Int, territory: Int) {
    Row(Modifier.fillMaxWidth().background(Color(0xFF191D20)).padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("🪵 $wood", fontSize = 13.sp); Text("🪨 $stone", fontSize = 13.sp); Text("🍖 $food", fontSize = 13.sp); Text("⚙ $steel", fontSize = 13.sp); Text("👥 $people", fontSize = 13.sp); Text("🪖 $soldiers", fontSize = 13.sp); Text("▣ $territory", fontSize = 13.sp)
    }
}

@Composable
fun CityView(modifier: Modifier, territory: Int) {
    Box(modifier.background(Color(0xFF26352B)).padding(16.dp)) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("OSTATNIA GRANICA", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("DZIEŃ 1", fontWeight = FontWeight.Bold)
            }
            Box(Modifier.fillMaxWidth().weight(1f).padding(20.dp).background(Color(0xFF324B35)), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("🏰", fontSize = 72.sp)
                    Text("Zamek / baza ocalałych", fontWeight = FontWeight.Bold)
                    Text("🌲   🪓   🏚️   ⛏️")
                    Text("Kontrolowane terytorium: $territory")
                    Text("Droga → las → złoże żelaza")
                }
            }
            Text("Mapa 2.5D • las • ruiny • kopalnia • drogi • wieże strażnicze", fontSize = 12.sp)
        }
    }
}

@Composable
fun ControlPanel(modifier: Modifier, onWood: () -> Unit, onSteel: () -> Unit, onTower: () -> Unit, onScout: () -> Unit, onRecruit: () -> Unit, log: List<String>) {
    Column(modifier.background(Color(0xFF171A1D)).padding(14.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("BAZA", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Pierwszy etap: zamek, zwiad, zasoby i rozszerzanie granicy.", fontSize = 13.sp)
        Button(onClick = onWood, Modifier.fillMaxWidth()) { Text("Praca tartaku  +25 drewna") }
        Button(onClick = onSteel, Modifier.fillMaxWidth()) { Text("Wytop stali  -20 drewna / +10 stali") }
        Button(onClick = onScout, Modifier.fillMaxWidth()) { Text("Wyślij zwiadowcę") }
        Button(onClick = onTower, Modifier.fillMaxWidth()) { Text("Zbuduj wieżę  -80 drewna / -50 kamienia") }
        Button(onClick = onRecruit, Modifier.fillMaxWidth()) { Text("Szkol rekruta  -10 żywności") }
        Divider(Modifier.padding(vertical = 8.dp))
        Text("DZIENNIK", fontWeight = FontWeight.Bold)
        log.forEach { Text("• $it", fontSize = 12.sp) }
    }
}
